package dev.team.readtoday.client;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import dev.team.readtoday.client.jersey.JerseyAuthController;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.view.auth.AuthController;
import dev.team.readtoday.client.view.home.HomeView;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

  private static final double WINDOW_WIDTH = 600.0;
  private static final double WINDOW_HEIGHT = 400.0;

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  private Scene homeScene;
  private Scene authScene;

  @Override
  public void init() throws IOException {
    JsonObject config = loadConfig();
    String baseUri = config.get("baseUri").getAsString();

    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(baseUri);

    URI googleOauthUri = URI.create(config.get("googleOauthUri").getAsString());
    AuthController authController = new JerseyAuthController(baseTarget);

    // TODO: create authScene using authController and googleOauthUri
    homeScene = createHomeScene();
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Home | ReadToday");
    stage.setScene(homeScene);
    stage.show();
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
