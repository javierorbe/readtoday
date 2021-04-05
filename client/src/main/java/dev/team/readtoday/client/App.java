package dev.team.readtoday.client;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import dev.team.readtoday.client.navigation.ChangeSceneEvent;
import dev.team.readtoday.client.storage.UserJwtTokenStorage;
import dev.team.readtoday.client.usecase.auth.AuthRequestListener;
import dev.team.readtoday.client.usecase.auth.accesstoken.AccessTokenReceiver;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpFailedEvent;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import dev.team.readtoday.client.usecase.create.ChannelCreationListener;
import dev.team.readtoday.client.usecase.search.SearchRequestListener;
import dev.team.readtoday.client.view.admin.AdminView;
import dev.team.readtoday.client.view.auth.AuthView;
import dev.team.readtoday.client.view.home.HomeView;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class App extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final double WINDOW_WIDTH = 600.0;
  private static final double WINDOW_HEIGHT = 400.0;

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  private static final long AUTH_RESPONSE_LISTENER_SHUTDOWN_DELAY = 5L;

  private final ExecutorService eventBusExecutor = Executors.newSingleThreadExecutor();
  private final EventBus eventBus = new AsyncEventBus(eventBusExecutor);

  private Stage stage;
  private Scene homeScene;
  private Scene authScene;
  private Scene adminScene;

  private AccessTokenReceiver accessTokenReceiver;

  @Override
  public void init() throws IOException {
    JsonObject config = loadConfig();

    URI baseRedirectUri = URI.create(config.get("oAuthBaseRedirectUri").getAsString());
    URI googleAccessTokenUri = buildGoogleAccessTokenUri(config, baseRedirectUri);

    AuthView authView = new AuthView(googleAccessTokenUri);
    HomeView homeView = new HomeView(eventBus, List.of());
    AdminView adminView = new AdminView(eventBus);

    WebTarget serverBaseTarget = getServerBaseTarget(config);

    eventBus.register(new AuthRequestListener(eventBus, serverBaseTarget));
    eventBus.register(new SearchRequestListener(eventBus, serverBaseTarget));
    eventBus.register(new ChannelCreationListener(eventBus, serverBaseTarget));
    eventBus.register(authView);
    eventBus.register(homeView);
    eventBus.register(adminView);
    eventBus.register(this);

    authScene = createScene("auth.fxml", authView);
    homeScene = createScene("home.fxml", homeView);
    adminScene = createScene("channelCreation.fxml", adminView);

    accessTokenReceiver = new AccessTokenReceiver(baseRedirectUri, eventBus, authView);
  }

  @Override
  public void start(Stage stage) {
    this.stage = stage;

    stage.setOnHiding(event -> {
      accessTokenReceiver.close();
      eventBusExecutor.shutdownNow();
    });

    stage.setTitle("ReadToday");
    stage.setScene(authScene);
    stage.show();
  }

  @Subscribe
  public void onSuccessfulSignUp(SuccessfulSignUpEvent event) {
    String token = event.getJwtToken();
    LOGGER.debug("Successful sign up (JWT Token = {})", token);
    UserJwtTokenStorage.setToken(token);
    Platform.runLater(() -> stage.setScene(homeScene));
    accessTokenReceiver.close();
  }

  @Subscribe
  public void onSuccessfulSignIn(SuccessfulSignInEvent event) {
    String token = event.getJwtToken();
    LOGGER.debug("Successful sign in (JWT Token = {})", token);
    UserJwtTokenStorage.setToken(token);
    Platform.runLater(() -> stage.setScene(homeScene));
    accessTokenReceiver.close();
  }

  // Easiest way I found to change scenes :c
  @Subscribe
  public void onChangeScene(ChangeSceneEvent event) {
    switch (event.getScene()) {
      case AUTH -> Platform.runLater(() -> stage.setScene(authScene));
      case ADMIN -> Platform.runLater(() -> stage.setScene(adminScene));
      case HOME -> Platform.runLater(() -> stage.setScene(homeScene));
      default -> throw new IllegalStateException("Unreachable");
    }
  }

  @Subscribe
  public static void onSignUpFailed(SignUpFailedEvent event) {
    LOGGER.debug("Sign up failed (reason: {}).", event.getReason());

    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Sign up failed");
      alert.setHeaderText("Sign up failed");
      alert.setContentText("Reason: " + event.getReason());
      alert.show();
    });
  }

  private static Scene createScene(String fxmlFile, Object controller) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(App.class.getResource("/fxml/" + fxmlFile));
    fxmlLoader.setController(controller);
    Parent root = fxmlLoader.load();
    return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  private static JsonObject loadConfig() throws FileNotFoundException {
    URL fileUrl = Objects.requireNonNull(App.class.getResource(CONFIG_FILE));
    String file = fileUrl.getFile();
    return GSON.fromJson(new JsonReader(new FileReader(file)), JsonObject.class);
  }

  private static WebTarget getServerBaseTarget(JsonObject config) {
    String serverBaseUri = config.get("serverBaseUri").getAsString();
    Client client = ClientBuilder.newClient();
    return client.target(serverBaseUri);
  }

  private static URI buildGoogleAccessTokenUri(JsonObject config, URI baseRedirectUri) {
    URI googleOauthBaseUri = URI.create(config.get("googleOauthBaseUri").getAsString());
    String googleClientId = config.get("googleClientId").getAsString();

    URI oauthRedirectUri = UriBuilder.fromUri(baseRedirectUri)
        .path("oauth")
        .build();

    return UriBuilder.fromUri(googleOauthBaseUri)
        .queryParam("client_id", googleClientId)
        .queryParam("redirect_uri", oauthRedirectUri)
        .build();
  }
}
