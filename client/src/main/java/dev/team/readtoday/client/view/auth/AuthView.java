package dev.team.readtoday.client.view.auth;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.auth.AuthInfoProvider;
import dev.team.readtoday.client.usecase.auth.AuthProcess;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.signin.SignInFailedEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpFailedEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AuthView implements ViewController, Initializable, AuthInfoProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthView.class);

  private final URI googleAccessTokenUri;
  private AuthProcess selectedAuthProcess = null;

  @FXML
  private TextField usernameField;
  @FXML
  private Button signUpBtn;
  @FXML
  private Button signInBtn;

  public AuthView(EventBus eventBus, URI googleAccessTokenUri) {
    this.googleAccessTokenUri = googleAccessTokenUri;
    eventBus.register(this);
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
    toggleAuthButtons(false);
    AlertLauncher.error("Sign up failed", event.getReason());
  }

  @Subscribe
  public void onSignInFailed(SignInFailedEvent event) {
    toggleAuthButtons(false);
    AlertLauncher.error("Sign in failed", event.getReason());
  }

  @Subscribe
  public void onSignedOut(SignedOutEvent event) {
    usernameField.setText("");
    toggleAuthButtons(false);
  }

  private void toggleAuthButtons(boolean state) {
    signUpBtn.setDisable(state);
    signInBtn.setDisable(state);
  }

  private void openAuthUri() {
    try {
      toggleAuthButtons(true);
      LOGGER.debug("Google OAuth Link: {}", googleAccessTokenUri);
      Desktop.getDesktop().browse(googleAccessTokenUri);
    } catch (IOException e) {
      LOGGER.error("Error opening OAuth URI.", e);
      toggleAuthButtons(false);
    }
  }
}
