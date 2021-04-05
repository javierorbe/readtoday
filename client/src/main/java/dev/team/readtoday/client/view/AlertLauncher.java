package dev.team.readtoday.client.view;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class AlertLauncher {

  public static void info(String title) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.INFORMATION, title);
      alert.show();
    });
  }

  public static void error(String title) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR, title);
      alert.show();
    });
  }

  public static void error(String title, String content) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR, title);
      alert.setContentText(content);
      alert.show();
    });
  }
}
