package dev.team.readtoday.client.view.home;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.client.app.gui.ChangeSceneEvent;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.channel.search.ChannelSearchRequestFailedEvent;
import dev.team.readtoday.client.usecase.channel.search.SearchChannelsByCategoryEvent;
import dev.team.readtoday.client.usecase.channel.search.SearchResultReceivedEvent;
import dev.team.readtoday.client.usecase.subscription.subscribe.SubscriptionRequestedEvent;
import dev.team.readtoday.client.usecase.subscription.unsubscribe.DeleteSubscriptionEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class HomeView implements ViewController, Initializable {

  private final EventBus eventBus;

  private final ImmutableList<Channel> allChannels;
  private final Map<Category, List<Channel>> categoryToChannel;

  @FXML
  private ListView<Channel> channelListView;
  private final ObservableList<Channel> observableChannelList;

  @FXML
  private ListView<Channel> newChannelListView;

  @FXML
  private ComboBox<Category> channelCategorySelector;

  @FXML
  private TextField channelsByCategory;

  public HomeView(EventBus eventBus, Collection<Channel> subscribedChannels) {
    this.eventBus = eventBus;
    allChannels = ImmutableList.sortedCopyOf(subscribedChannels);
    observableChannelList = FXCollections.observableArrayList(allChannels);
    categoryToChannel = createCategoryToChannelMap(subscribedChannels);

    eventBus.register(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Objects.requireNonNull(channelListView);
    Objects.requireNonNull(newChannelListView);
    Objects.requireNonNull(channelCategorySelector);
    Objects.requireNonNull(channelsByCategory);

    // Set selector categories.
    List<Category> categories = new ArrayList<>(categoryToChannel.keySet());
    categories.sort(Comparator.naturalOrder());
    channelCategorySelector.getItems().addAll(categoryToChannel.keySet());

    // Initially, all channels are visible.
    channelListView.setItems(observableChannelList);

    channelCategorySelector.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        observableChannelList.setAll(allChannels);
      } else {
        observableChannelList.setAll(categoryToChannel.get(newValue));
      }
    });

    channelListView.setCellFactory(listView -> new ChannelCell(eventBus));
    newChannelListView.setCellFactory(listView -> new ChannelCell(eventBus));
  }

  private static Map<Category, List<Channel>> createCategoryToChannelMap(
      Collection<Channel> channels) {
    Map<Category, List<Channel>> map = new HashMap<>(channels.size()); // Size approximation.

    for (Channel channel : channels) {
      for (Category category : channel.getCategories()) {
        List<Channel> categoryChannels =
            map.computeIfAbsent(category, ignored -> new ArrayList<>(10));
        categoryChannels.add(channel);
      }
    }

    map.forEach((category, channelList) -> channelList.sort(Comparator.naturalOrder()));

    return map;
  }

  @FXML
  public void unselectCategory() {
    channelCategorySelector.valueProperty().set(null);
  }

  @FXML
  public void performSearch() {
    String categoryName = channelsByCategory.getText();
    eventBus.post(new SearchChannelsByCategoryEvent(categoryName));
  }

  @FXML
  public void goToAdmin() {
    eventBus.post(new ChangeSceneEvent(SceneType.ADMIN));
  }

  @FXML
  private void signOut(ActionEvent event) {
    eventBus.post(new SignedOutEvent());
  }

  @FXML
  public void unsubscribe() {
    if (channelListView.getSelectionModel().getSelectedItem() != null) {
      String channelId = channelListView.getSelectionModel().getSelectedItem().getId();
      eventBus.post(new DeleteSubscriptionEvent(channelId));
    }
  }

  @FXML
  public void subscribe() {
    if (newChannelListView.getSelectionModel().getSelectedItem() != null) {
      String channelId = newChannelListView.getSelectionModel().getSelectedItem().getId();
      eventBus.post(new SubscriptionRequestedEvent(channelId));
    }
  }

  @Subscribe
  public void onSearchResultReceived(SearchResultReceivedEvent event) {
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(event.getChannels()));
    Platform.runLater(() -> newChannelListView.setItems(list));
  }

  @Subscribe
  public void onChannelSearchRequestFailed(ChannelSearchRequestFailedEvent event) {
    AlertLauncher.error("Category not found");
  }
}
