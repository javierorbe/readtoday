package dev.team.readtoday.client.view.auth;

import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.auth.AuthInfoProvider;
import dev.team.readtoday.client.usecase.auth.AuthProcess;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.signin.SignInFailedEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpFailedEvent;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AuthView implements Initializable, AuthInfoProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthView.class);

  private final URI googleAccessTokenUri;

  @FXML
  private TextField usernameField;
  @FXML
  private Button signUpBtn;
  @FXML
  private Button signInBtn;

  private AuthProcess selectedAuthProcess = null;

  public AuthView(URI googleAccessTokenUri) {
    this.googleAccessTokenUri = googleAccessTokenUri;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(usernameField);
    Objects.requireNonNull(signUpBtn);
    Objects.requireNonNull(signInBtn);
  }

  @Override
  public AuthProcess getAuthProcess() {
    Objects.requireNonNull(selectedAuthProcess);
    return selectedAuthProcess;
  }

  @Override
  public String getUsername() {
    if (selectedAuthProcess != AuthProcess.SIGN_UP) {
      throw new IllegalStateException("Not in the sign up process.");
    }
    return usernameField.getText();
  }

  @FXML
  public void openSignUpAuthUri() {
    selectedAuthProcess = AuthProcess.SIGN_UP;
    openAuthUri();
  }

  @FXML
  public void openSignInAuthUri() {
    selectedAuthProcess = AuthProcess.SIGN_IN;
    openAuthUri();
  }

  @Subscribe
  public void onSignUpFailed(SignUpFailedEvent event) {
    LOGGER.debug("Sign in failed (reason: {}).", event.getReason());

    signUpBtn.setDisable(false);
    signInBtn.setDisable(false);

    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Sign up failed");
      alert.setHeaderText("Sign up failed");
      alert.setContentText("Reason: " + event.getReason());
      alert.show();
    });
  }

  @Subscribe
  public void onSignInFailed(SignInFailedEvent event) {
    LOGGER.debug("Sign up failed (reason: {}).", event.getReason());

    signUpBtn.setDisable(false);
    signInBtn.setDisable(false);

    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Sign in failed");
      alert.setHeaderText("Sign in failed");
      alert.setContentText("Reason: " + event.getReason());
      alert.show();
    });
  }

  @Subscribe
  public void onSignedOut(SignedOutEvent event) {
    usernameField.setText("");
    signUpBtn.setDisable(false);
    signInBtn.setDisable(false);
  }

  private void openAuthUri() {
    try {
      signUpBtn.setDisable(true);
      signInBtn.setDisable(true);

      LOGGER.debug("Google OAuth Link: {}", googleAccessTokenUri);
      Desktop.getDesktop().browse(googleAccessTokenUri);
    } catch (IOException e) {
      LOGGER.error("Error opening OAuth URI.", e);
      signUpBtn.setDisable(false);
      signInBtn.setDisable(false);
    }
  }
}
