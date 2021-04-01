package dev.team.readtoday.client;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import dev.team.readtoday.client.jersey.JerseyAuthController;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.oauth.AuthResponseServer;
import dev.team.readtoday.client.oauth.JwtTokenReceiver;
import dev.team.readtoday.client.view.auth.AuthController;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class App extends Application implements JwtTokenReceiver {

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

  private Scene homeScene;
  private Scene authScene;

  private AuthResponseServer oAuthRespListener;

  @Override
  public void init() throws IOException {
    JsonObject config = loadConfig();

    URI oAuthBaseRedirectUri = URI.create(config.get("oAuthBaseRedirectUri").getAsString());
    URI googleOauthBaseUri = URI.create(config.get("googleOauthBaseUri").getAsString());
    String googleClientId = config.get("googleClientId").getAsString();

    URI oAuthRedirectUri = UriBuilder.fromUri(oAuthBaseRedirectUri)
        .path("oauth")
        .build();

    URI googleOauthUri = UriBuilder.fromUri(googleOauthBaseUri)
        .queryParam("client_id", googleClientId)
        .queryParam("redirect_uri", oAuthRedirectUri)
        .build();

    String baseUri = config.get("serverBaseUri").getAsString();
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(baseUri);

    AuthController authController = new JerseyAuthController(baseTarget);
    AuthView authView = new AuthView(googleOauthUri);

    oAuthRespListener =
        new AuthResponseServer(oAuthBaseRedirectUri, authView, authController, this);

    authScene = createScene("auth.fxml", authView);
    homeScene = createHomeScene();
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Home | ReadToday");
    stage.setScene(authScene);
    stage.show();
  }

  @Override
  public void receiveJwtToken(String jwtToken) {
    LOGGER.debug("Received JWT token: {}", jwtToken);

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.schedule(() -> oAuthRespListener.close(),
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
}
