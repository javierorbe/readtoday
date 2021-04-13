package dev.team.readtoday.client;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import dev.team.readtoday.client.app.jersey.JerseyHttpRequestBuilderFactory;
import dev.team.readtoday.client.navigation.ChangeSceneEvent;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.accesstoken.AccessTokenReceiver;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequestListener;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpRequestListener;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import dev.team.readtoday.client.usecase.channel.create.ChannelCreationListener;
import dev.team.readtoday.client.usecase.channel.search.SearchRequestListener;
import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import dev.team.readtoday.client.usecase.subscription.subscribe.SubscriptionListener;
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
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class App extends Application implements AuthTokenSupplier {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final double WINDOW_WIDTH = 600.0;
  private static final double WINDOW_HEIGHT = 400.0;

  private static final String CONFIG_FILE = "/config.json";

  private final ExecutorService eventBusExecutor = Executors.newCachedThreadPool();
  private final EventBus eventBus = new AsyncEventBus(eventBusExecutor);

  private Stage stage;
  private Scene homeScene;
  private Scene authScene;
  private Scene adminScene;

  private AccessTokenReceiver accessTokenReceiver;
  private String authToken;

  @Override
  public void init() throws IOException {
    JsonObject config = loadConfig();

    URI baseRedirectUri = URI.create(config.get("oAuthBaseRedirectUri").getAsString());
    URI googleAccessTokenUri = buildGoogleAccessTokenUri(config, baseRedirectUri);

    final AuthView authView = new AuthView(eventBus, googleAccessTokenUri);
    final HomeView homeView = new HomeView(eventBus, List.of());
    final AdminView adminView = new AdminView(eventBus);

    registerRequestListeners(config, this);
    eventBus.register(this);

    authScene = createScene("auth.fxml", authView);
    homeScene = createScene("home.fxml", homeView);
    adminScene = createScene("channelCreation.fxml", adminView);

    accessTokenReceiver = new AccessTokenReceiver(baseRedirectUri, eventBus, authView);
  }

  private void registerRequestListeners(JsonObject config, AuthTokenSupplier ats) {
    var factory = new JerseyHttpRequestBuilderFactory(config, ats);

    new SignUpRequestListener(eventBus, factory);
    new SignInRequestListener(eventBus, factory);
    new SearchRequestListener(eventBus, factory);
    new ChannelCreationListener(eventBus, factory);
    new SubscriptionListener(eventBus, factory);
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
    authToken = event.getJwtToken();
    setScene(homeScene);
    accessTokenReceiver.close();
  }

  @Subscribe
  public void onSuccessfulSignIn(SuccessfulSignInEvent event) {
    authToken = event.getJwtToken();
    setScene(homeScene);
    accessTokenReceiver.close();
  }

  @Subscribe
  public void onSignedOut(SignedOutEvent event) {
    authToken = null;
    accessTokenReceiver.start();
    setScene(authScene);
  }

  @Subscribe
  public void onChangeScene(ChangeSceneEvent event) {
    switch (event.getScene()) {
      case AUTH -> Platform.runLater(() -> stage.setScene(authScene));
      case ADMIN -> Platform.runLater(() -> stage.setScene(adminScene));
      case HOME -> Platform.runLater(() -> stage.setScene(homeScene));
      default -> throw new IllegalStateException("Unreachable");
    }
  }

  @Override
  public String getAuthToken() {
    return authToken;
  }

  private void setScene(Scene scene) {
    Platform.runLater(() -> stage.setScene(scene));
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
    Gson gson = new Gson();
    return gson.fromJson(new JsonReader(new FileReader(file)), JsonObject.class);
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
