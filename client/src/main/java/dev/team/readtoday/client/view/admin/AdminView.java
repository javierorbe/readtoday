package dev.team.readtoday.client.view.admin;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.gui.ChangeSceneEvent;
import dev.team.readtoday.client.app.gui.SceneCreator;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesSuccessfullyEvent;
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
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class AdminView implements ViewController, Initializable {

  private final EventBus eventBus;

  private final Stage categoryCreationStage;

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
    categoryCreationStage = new Stage();
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
  public void createCategory() {
    Scene categoryCreationScene = SceneCreator.createScene(
        "categoryCreation.fxml",
        new CategoryCreationView(eventBus)
    );

    categoryCreationStage.setHeight(150);
    categoryCreationStage.setWidth(300);
    categoryCreationStage.setTitle("Category Creation");
    categoryCreationStage.setScene(categoryCreationScene);
    categoryCreationStage.show();
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

  @Subscribe
  public void onSearchAllCategoriesSuccessfullyEvent(SearchAllCategoriesSuccessfullyEvent event) {
    // TODO: Get categories in admin view or at app start?
    ImmutableCollection<Category> categories = event.getCategories();
  }
}
