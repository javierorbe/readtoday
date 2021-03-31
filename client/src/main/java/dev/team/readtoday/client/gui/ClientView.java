package dev.team.readtoday.client.gui;

import java.net.URI;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ClientView extends Application {

  @FXML
  private Button login = new Button();
  @FXML
  private Button register = new Button();

  private Stage stage = new Stage();

  @Override
  public void start(Stage primaryStage) {

    try {
      stage = primaryStage;
      stage.setTitle("HomePage");
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("fxml/HomePage.fxml"));
      ScrollPane rootLayout = (ScrollPane) loader.load();

      Scene scene = new Scene(rootLayout);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleRegisterView() {
    try {
      Stage stage = (Stage) register.getScene().getWindow();
      stage.close();
      open("https://accounts.google.com/o/oauth2/v2/auth");

      stage.setTitle("Register");
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("fxml/Register.fxml"));
      ScrollPane rootLayout = (ScrollPane) loader.load();

      Scene scene = new Scene(rootLayout);
      stage.setScene(scene);
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void open(String url) throws Exception {
    URI u = new URI(url);
    java.awt.Desktop.getDesktop().browse(u);
  }

  public static void main(String[] args) {
    launch(args);
  }
}