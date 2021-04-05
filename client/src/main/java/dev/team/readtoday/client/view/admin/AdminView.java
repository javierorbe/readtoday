package dev.team.readtoday.client.view.admin;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.navigation.ChangeSceneEvent;
import dev.team.readtoday.client.navigation.SceneType;
import dev.team.readtoday.client.usecase.create.ChannelCreationEvent;
import dev.team.readtoday.client.usecase.create.ChannelCreationFailedEvent;
import dev.team.readtoday.client.usecase.create.ChannelCreationRequest;
import dev.team.readtoday.client.usecase.create.ChannelSuccessfullyCreatedEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public final class AdminView implements Initializable {

  @FXML
  private TextField title;
  @FXML
  private TextField rssUrl;
  @FXML
  private TextField description;
  @FXML
  private TextField imageUrl;

  // TODO: Adds categories ids

  private final EventBus eventBus;

  public AdminView(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(title);
    Objects.requireNonNull(rssUrl);
    Objects.requireNonNull(description);
    Objects.requireNonNull(imageUrl);
  }

  @FXML
  public void createChannel() {
    ChannelCreationRequest request = new ChannelCreationRequest(
        title.getText(),
        rssUrl.getText(),
        description.getText(),
        imageUrl.getText(),
        // TODO: Change for the real categories ids!
        Collections.emptyList()
    );

    eventBus.post(new ChannelCreationEvent(request));
  }

  @FXML
  public void exitAdminMode() {
    eventBus.post(new ChangeSceneEvent(SceneType.HOME));
  }

  @Subscribe
  public static void onChannelSuccessfullyCreated(ChannelSuccessfullyCreatedEvent event) {
    AlertLauncher.info("Channel was created");
  }

  @Subscribe
  public static void onChannelCreationFailedEvent(ChannelCreationFailedEvent event) {
    AlertLauncher.error("Channel creation failed", event.getReason());
  }
}
