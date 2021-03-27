package dev.team.readtoday.client.gui;

import javafx.scene.control.TextField;

public class Handler {


  public static void addNewUser(TextField username, TextField email) {
    //TODO: Check if the username and email are available and save them in the DB
    String newuser = username.getText();
    String newmail = email.getText();
  }
}
