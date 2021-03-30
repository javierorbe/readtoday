package dev.team.readtoday.client;

import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class GuiApp extends Application {

  private static final Collection<Channel> EXAMPLE_CHANNELS = ImmutableSet.of(
      new Channel(UUID.randomUUID(),
          "El Correo",
          "https://static1.elcorreo.com/squido/latest/assets/icons/elcorreo/favicon-16x16.png"),
      new Channel(UUID.randomUUID(),
          "DEV Community",
          "https://res.cloudinary.com/practicaldev/image/fetch/s--E8ak4Hr1--/c_limit,f_auto,fl_progressive,q_auto,w_32/https://dev-to.s3.us-east-2.amazonaws.com/favicon.ico")
  );

  private static final double WINDOW_WIDTH = 600;
  private static final double WINDOW_HEIGHT = 400;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("/fxml/home.fxml"));
    fxmlLoader.setController(new HomeController(EXAMPLE_CHANNELS));

    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    stage.setTitle("Home | ReadToday");
    stage.setScene(scene);
    stage.show();
  }
}
