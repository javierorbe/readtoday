package dev.team.readtoday.client.view.auth;

import dev.team.readtoday.client.oauth.AuthInfoProvider;
import dev.team.readtoday.client.oauth.AuthProcess;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AuthView implements Initializable, AuthInfoProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthView.class);

  private final URI googleAuthUri;

  @FXML
  private TextField usernameField;

  private AuthProcess selectedAuthProcess = null;

  public AuthView(URI googleAuthUri) {
    this.googleAuthUri = googleAuthUri;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(usernameField);
  }

  @Override
  public AuthProcess getAuthProcess() {
    Objects.requireNonNull(selectedAuthProcess);
    return selectedAuthProcess;
  }

  @Override
  public String getUsername() {
    return usernameField.getText();
  }

  public void openSignUpAuthUri() {
    try {
      Desktop.getDesktop().browse(googleAuthUri);
      LOGGER.debug("Google OAuth Link: {}", googleAuthUri);
      selectedAuthProcess = AuthProcess.SIGN_UP;
    } catch (IOException e) {
      LOGGER.error("Error opening OAuth URI.", e);
    }
  }

  public void openSignInAuthUri() {
    try {
      Desktop.getDesktop().browse(googleAuthUri);
      LOGGER.debug("Google OAuth Link: {}", googleAuthUri);
      selectedAuthProcess = AuthProcess.SIGN_IN;
    } catch (IOException e) {
      LOGGER.error("Error opening OAuth URI.", e);
    }
  }
}
