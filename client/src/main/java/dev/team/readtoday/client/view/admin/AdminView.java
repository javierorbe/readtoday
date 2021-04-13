package dev.team.readtoday.client.view.admin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import dev.team.readtoday.client.app.gui.ChangeSceneEvent;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.channel.create.ChannelCreationEvent;
import dev.team.readtoday.client.usecase.channel.create.ChannelCreationFailedEvent;
import dev.team.readtoday.client.usecase.channel.create.ChannelCreationRequest;
import dev.team.readtoday.client.usecase.channel.create.ChannelSuccessfullyCreatedEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public final class AdminView implements ViewController, Initializable {

  private final EventBus eventBus;

  @FXML
  private TextField title;
  @FXML
  private TextField rssUrl;
  @FXML
  private TextField description;
  @FXML
  private TextField imageUrl;

  // TODO: Adds categories ids

  public AdminView(EventBus eventBus) {
    this.eventBus = eventBus;
    eventBus.register(this);
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

  @FXML
  private void signOut() {
    eventBus.post(new SignedOutEvent());
  }

  @Subscribe
  public void onChannelSuccessfullyCreated(ChannelSuccessfullyCreatedEvent event) {
    AlertLauncher.info("Channel was created");
  }

  @Subscribe
  public void onChannelCreationFailedEvent(ChannelCreationFailedEvent event) {
    AlertLauncher.error("Channel creation failed", event.getReason());
  }
}
