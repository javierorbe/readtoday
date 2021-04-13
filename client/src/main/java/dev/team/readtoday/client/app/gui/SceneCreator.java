package dev.team.readtoday.client.app.gui;

import dev.team.readtoday.client.view.ViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

enum SceneCreator {
  ;

  private static final double WINDOW_WIDTH = 600.0;
  private static final double WINDOW_HEIGHT = 400.0;

  /**
   * Create a {@link Scene} from an FXML file and a controller.
   *
   * @param fxmlFile FXML filepath in the {@code fxml/} resources folder
   * @param controller the controller associated with the view
   * @return the scene created from the FXML file
   */
  static Scene createScene(String fxmlFile, ViewController controller) {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(SceneCreator.class.getResource("/fxml/" + fxmlFile));
    fxmlLoader.setController(controller);
    try {
      Parent root = fxmlLoader.load();
      return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error loading FXML file.", e);
    }
  }
}
