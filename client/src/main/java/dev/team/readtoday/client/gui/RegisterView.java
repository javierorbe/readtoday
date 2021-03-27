package dev.team.readtoday.client.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView extends Application{
	
	public void start(Stage primaryStage) {
		try {
			VBox vbox = new VBox();
			BorderPane base = new BorderPane();
			
			HBox hbox1 = new HBox();
			HBox hbox2 = new HBox();
			
			Label labeluser = new Label("Username");
			TextField username = new TextField();
			Label labelmail = new Label("E-mail");
			TextField email = new TextField();
			Button sumbit = new Button("Sumbit");
			
			sumbit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					Handler.addNewUser(username, email);
				}
			});		
			
			hbox1.setSpacing(50);
			hbox1.setAlignment(Pos.CENTER);
			hbox1.getChildren().addAll(labeluser, username);
			
			hbox2.setSpacing(70);
			hbox2.setAlignment(Pos.CENTER);
			hbox2.getChildren().addAll(labelmail, email);
			
			vbox.setSpacing(10);
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(hbox1, hbox2, sumbit);
			
			Scene scene = new Scene(vbox,400,400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Register");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
