package dev.team.readtoday.client.app.gui;

public enum SceneType {
  AUTH("auth.fxml"),
  HOME("home.fxml"),
  ADMIN("channelCreation.fxml");

  private final String fxmlFile;

  SceneType(String fxmlFile) {
    this.fxmlFile = fxmlFile;
  }

  public String getFxmlFile() {
    return fxmlFile;
  }
}
