package dev.team.readtoday.client.app.gui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import dev.team.readtoday.client.view.ViewController;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class SceneContainer {

  private final Supplier<? extends Stage> stageSupplier;
  private final Map<SceneType, Scene> scenes = new EnumMap<>(SceneType.class);

  public SceneContainer(EventBus eventBus,
                        Map<SceneType, ViewController> controllers,
                        Supplier<? extends Stage> stageSupplier) {
    this.stageSupplier = stageSupplier;

    controllers.forEach((sceneType, controller) -> {
      Scene scene = SceneCreator.createScene(sceneType.getFxmlFile(), controller);
      scenes.put(sceneType, scene);
    });

    eventBus.register(this);
  }

  @Subscribe
  public void onChangeScene(ChangeSceneEvent event) {
    setScene(event.getScene());
  }

  @Subscribe
  public void onSuccessfulSignUp(SuccessfulSignUpEvent event) {
    setScene(SceneType.HOME);
  }

  @Subscribe
  public void onSuccessfulSignIn(SuccessfulSignInEvent event) {
    setScene(SceneType.HOME);
  }

  @Subscribe
  public void onSignedOut(SignedOutEvent event) {
    setScene(SceneType.AUTH);
  }

  public void setScene(SceneType scene) {
    Platform.runLater(() -> stageSupplier.get().setScene(scenes.get(scene)));
  }
}
