package dev.team.readtoday.client.view.admin;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.gui.ChangeSceneEvent;
import dev.team.readtoday.client.app.gui.SceneCreator;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategorySuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesSuccessfullyEvent;
import dev.team.readtoday.client.usecase.channel.edit.events.ChannelEditedSuccessfully;
import dev.team.readtoday.client.usecase.channel.edit.events.ChannelEditionFailed;
import dev.team.readtoday.client.usecase.channel.edit.events.EditChannelEvent;
import dev.team.readtoday.client.usecase.channel.edit.messages.EditChannelRequest;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategorySuccessfullyEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class Admin1View implements ViewController, Initializable {

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
  @FXML
  private TextField channelsByCategory;
  @FXML
  private ListView<Channel> newChannelListView;

  private ObservableList<Node> observableCategories;

  private Map<CheckBox, String> checkBoxCategoryIdMap;

  private String channelId;

  public Admin1View(EventBus eventBus) {
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
    Objects.requireNonNull(newChannelListView);
  }

  @FXML
  public void saveChannel() {
    List<String> selectedCategoriesIds = observableCategories.stream()
        .map(node -> (CheckBox) node)
        .filter(CheckBox::isSelected)
        .map(checkBox -> checkBoxCategoryIdMap.get(checkBox))
        .collect(Collectors.toList());

    EditChannelRequest request = new EditChannelRequest(
        title.getText(),
        rssUrl.getText(),
        description.getText(),
        imageUrl.getText(),
        selectedCategoriesIds
    );

    eventBus.post(new EditChannelEvent(channelId, request));


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

  @FXML
  private void createScene() {
    eventBus.post(new ChangeSceneEvent((SceneType.ADMIN)));
  }

  @FXML
  private void editScene() {
    eventBus.post(new ChangeSceneEvent((SceneType.ADMIN1)));
  }

  @FXML
  public void performSearch() {
    String categoryName = channelsByCategory.getText();
    eventBus.post(new SearchChannelsByCategoryEvent(categoryName));
  }

  @FXML
  public void loadChannel() {
    Channel channel = newChannelListView.getSelectionModel().getSelectedItem();
    channelId = channel.getId();
    title.setText(channel.getName());
    rssUrl.setText(channel.getRssUrl());
    description.setText(channel.getDescription());
    imageUrl.setText(channel.getFaviconImageUrl());
  }

  @Subscribe
  public void onChannelSuccessfullyEdited(ChannelEditedSuccessfully event) {
    AlertLauncher.info("Channel was modified");
  }

  @Subscribe
  public void onChannelEditionFailed(ChannelEditionFailed event) {
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

  @Subscribe
  public void onSearchResultReceived(SearchChannelsByCategorySuccessfullyEvent event) {
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(event.getChannels()));
    Platform.runLater(() -> newChannelListView.setItems(list));

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

