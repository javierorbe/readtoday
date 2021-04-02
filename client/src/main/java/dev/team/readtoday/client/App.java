package dev.team.readtoday.client;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import dev.team.readtoday.client.auth.AuthRequestListener;
import dev.team.readtoday.client.auth.SuccessfulSignUpEvent;
import dev.team.readtoday.client.auth.accesstoken.AccessTokenReceiver;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
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
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class App extends Application {

  private static final Collection<Channel> EXAMPLE_CHANNELS = ImmutableSet.of(
      new Channel(UUID.randomUUID(),
          "El Correo",
          "https://static1.elcorreo.com/squido/latest/assets/icons/elcorreo/favicon-16x16.png",
          ImmutableSet.of(new Category(UUID.randomUUID(), "News"))
      ),
      new Channel(UUID.randomUUID(),
          "DEV Community",
          "https://res.cloudinary.com/practicaldev/image/fetch/s--E8ak4Hr1--/c_limit,f_auto,fl_progressive,q_auto,w_32/https://dev-to.s3.us-east-2.amazonaws.com/favicon.ico",
          ImmutableSet.of(new Category(UUID.randomUUID(), "Software Development"))
      ),
      new Channel(UUID.randomUUID(),
          "r/memes",
          "https://www.redditstatic.com/desktop2x/img/favicon/favicon-16x16.png",
          ImmutableSet.of(new Category(UUID.randomUUID(), "Entertainment"))
      )
  );

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final double WINDOW_WIDTH = 600.0;
  private static final double WINDOW_HEIGHT = 400.0;

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  private static final long AUTH_RESPONSE_LISTENER_SHUTDOWN_DELAY = 5L;

  private final EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());

  private Stage stage;
  private Scene homeScene;
  private Scene authScene;

  private AccessTokenReceiver accessTokenReceiver;

  @Override
  public void init() throws IOException {
    JsonObject config = loadConfig();

    URI baseRedirectUri = URI.create(config.get("oAuthBaseRedirectUri").getAsString());
    URI googleAccessTokenUri = buildGoogleAccessTokenUri(config, baseRedirectUri);

    AuthView authView = new AuthView(googleAccessTokenUri);

    accessTokenReceiver = new AccessTokenReceiver(baseRedirectUri, eventBus, authView);

    authScene = createScene("auth.fxml", authView);
    homeScene = createHomeScene();

    WebTarget serverBaseTarget = getServerBaseTarget(config);
    eventBus.register(new AuthRequestListener(eventBus, serverBaseTarget));
    eventBus.register(this);
  }

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    stage.setTitle("Home | ReadToday");
    stage.setScene(authScene);
    stage.show();
  }

  @Subscribe
  public void onSuccessfulSignUp(SuccessfulSignUpEvent event) {
    LOGGER.debug("Successful sign up (JWT Token = {})", event.getJwtToken());

    Platform.runLater(() -> stage.setScene(homeScene));

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.schedule(() -> accessTokenReceiver.close(),
        AUTH_RESPONSE_LISTENER_SHUTDOWN_DELAY, TimeUnit.SECONDS);
  }

  private static Scene createHomeScene() throws IOException {
    return createScene("home.fxml", new HomeView(EXAMPLE_CHANNELS));
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
