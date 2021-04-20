package dev.team.readtoday.client.view.admin;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.gui.ChangeSceneEvent;
import dev.team.readtoday.client.app.gui.SceneCreator;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategorySuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesSuccessfullyEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelCreationEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelCreationFailedEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelSuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.channel.create.messages.ChannelCreationRequest;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
  @FXML
  private VBox categorySelector;

  private ObservableList<Node> observableCategories;

  private Map<CheckBox, String> checkBoxCategoryIdMap;

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
    Objects.requireNonNull(categorySelector);
  }

  @FXML
  public void createChannel() {
    List<String> selectedCategoriesIds = observableCategories.stream()
        .map(node -> (CheckBox) node)
        .filter(CheckBox::isSelected)
        .map(checkBox -> checkBoxCategoryIdMap.get(checkBox))
        .collect(Collectors.toList());

    ChannelCreationRequest request = new ChannelCreationRequest(
        title.getText(),
        rssUrl.getText(),
        description.getText(),
        imageUrl.getText(),
        selectedCategoriesIds
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
    ImmutableCollection<Category> categories = event.getCategories();

    // Load category map
    Platform.runLater(() -> {
      checkBoxCategoryIdMap = getCheckBoxCategoryIdMap(categories);
      updateCategorySelectorGUI(checkBoxCategoryIdMap);
    });
  }

  private void updateCategorySelectorGUI(Map<CheckBox, String> map) {
    ObservableList<Node> children = categorySelector.getChildren();
    // Adds categories to gui
    children.setAll(map.keySet());
    observableCategories = children;
  }

  private Map<CheckBox, String> getCheckBoxCategoryIdMap(ImmutableCollection<Category> categories) {
    Map<CheckBox, String> categoriesIdByCheckbox = new HashMap<>();
    for (Category category : categories) {
      CheckBox checkBox = new CheckBox(category.getName());
      categoriesIdByCheckbox.put(checkBox, category.getId());
    }
    return categoriesIdByCheckbox;
  }

  @Subscribe
  public void onCategoryCreatedEvent(CategorySuccessfullyCreatedEvent event) {
    eventBus.post(new SearchAllCategoriesEvent());
  }
}
