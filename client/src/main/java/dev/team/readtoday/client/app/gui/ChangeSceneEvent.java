package dev.team.readtoday.client.app.gui;

public final class ChangeSceneEvent {

  private final SceneType scene;

  public ChangeSceneEvent(SceneType scene) {
    this.scene = scene;
  }

  public SceneType getScene() {
    return scene;
  }
}
