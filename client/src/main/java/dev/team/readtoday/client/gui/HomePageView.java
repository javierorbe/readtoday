package dev.team.readtoday.client.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageView extends Application {

  @Override
public void start(Stage primaryStage) {
    try {
      //BorderPane root = new BorderPane();
      Button btnlog = new Button("Login");
      Button btnreg = new Button("Register");
      btnlog.setPrefSize(150, 25);
      btnreg.setPrefSize(150, 25);
      VBox vbox = new VBox();
      btnreg.setOnAction(new EventHandler<ActionEvent>() {
        @Override
      public void handle(ActionEvent arg0) { 
          vbox.getScene().getWindow().hide();
          RegisterView reg = new RegisterView();
          reg.start(primaryStage);
          }
      });
      vbox.setSpacing(50);
      vbox.setAlignment(Pos.CENTER);
      vbox.getChildren().addAll(btnlog, btnreg);

      Scene scene = new Scene(vbox, 400, 400);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Home Page");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}
