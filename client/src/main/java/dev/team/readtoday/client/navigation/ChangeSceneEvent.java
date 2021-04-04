package dev.team.readtoday.client.navigation;

public class ChangeSceneEvent {

  private SceneType scene;

  public ChangeSceneEvent(SceneType scene) {
    this.scene = scene;
  }

  public SceneType getScene() {
    return scene;
  }

  public void setScene(SceneType scene) {
    this.scene = scene;
  }
}
