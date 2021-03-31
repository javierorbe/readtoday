package dev.team.readtoday.client.view.auth;

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

public final class AuthView implements Initializable {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthView.class);

  private final AuthController authController;
  private final URI googleOauthUri;

  @FXML
  private TextField usernameField;
  @FXML
  private TextField signUpTokenField;
  @FXML
  private TextField signInTokenField;

  public AuthView(AuthController authController, URI googleOauthUri) {
    this.authController = authController;
    this.googleOauthUri = googleOauthUri;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(usernameField);
    Objects.requireNonNull(signUpTokenField);
    Objects.requireNonNull(signInTokenField);
  }

  public void executeSignUpWithGoogle() {
    openAuthUri();
  }

  public void executeSignInWithGoogle() {
    openAuthUri();
  }

  public void sendSignUpToken() {
    String token = signUpTokenField.getText();
    String username = usernameField.getText();
    String jwtToken = authController.signUp(token, username);
    LOGGER.info("SignUp JWT Token: {}", jwtToken);
  }

  public void sendSignInToken() {
    String token = signInTokenField.getText();
    String jwtToken = authController.signIn(token);
    LOGGER.info("Sign In JWT Token: {}", jwtToken);
  }

  private void openAuthUri() {
    try {
      Desktop.getDesktop().browse(googleOauthUri);
    } catch (IOException e) {
      LOGGER.error("Error opening OAuth URI.", e);
    }
  }
}
