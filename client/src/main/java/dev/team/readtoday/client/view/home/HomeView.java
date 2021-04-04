package dev.team.readtoday.client.view.home;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.navigation.ChangeSceneEvent;
import dev.team.readtoday.client.navigation.SceneType;
import dev.team.readtoday.client.search.SearchChannelsByCategoryEvent;
import dev.team.readtoday.client.search.SearchResultReceivedEvent;
import dev.team.readtoday.client.storage.UserJwtTokenStorage;
import java.io.IOException;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class HomeView implements Initializable {

  private static final double CHANNEL_FAVICON_FIT_HEIGHT = 16.0;

  @FXML
  private ListView<Channel> channelListView;
  private final ObservableList<Channel> observableChannelList;

  @FXML
  private ListView<Channel> newChannelListView;

  @FXML
  private ComboBox<Category> channelCategorySelector;

  private final Map<Channel, Image> channelImageCache = new HashMap<>();

  private final EventBus eventBus;

  private final ImmutableList<Channel> allChannels;
  private final Map<Category, List<Channel>> categoryToChannel;

  @FXML
  private TextField channelsByCategory;

  public HomeView(EventBus eventBus, Collection<Channel> subscribedChannels) {
    this.eventBus = eventBus;
    allChannels = ImmutableList.sortedCopyOf(subscribedChannels);
    observableChannelList = FXCollections.observableArrayList(allChannels);
    categoryToChannel = createCategoryToChannelMap(subscribedChannels);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Objects.requireNonNull(channelListView);
    Objects.requireNonNull(channelCategorySelector);

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

    channelListView.setCellFactory(listView -> new CustomListCell());
    newChannelListView.setCellFactory(listView -> new CustomListCell());
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


  @Subscribe
  public void onSearchResultReceived(SearchResultReceivedEvent event) {
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(event.getChannels()));
    Platform.runLater(() -> newChannelListView.setItems(list));
  }

  @FXML
  private void signOut(ActionEvent event) throws IOException {
    UserJwtTokenStorage.removeToken();
    eventBus.post(new ChangeSceneEvent(SceneType.AUTH));
  }

  private final class CustomListCell extends ListCell<Channel> {

    private final ImageView imageView = new ImageView();

    private CustomListCell() {
      imageView.setPreserveRatio(true);
      imageView.setFitHeight(CHANNEL_FAVICON_FIT_HEIGHT);
    }

    @Override
    protected void updateItem(Channel channel, boolean empty) {
      super.updateItem(channel, empty);

      if (empty) {
        setText(null);
        setGraphic(null);
      } else {
        Image image = channelImageCache.computeIfAbsent(channel,
            ignored -> new Image(channel.getFaviconImageUrl()));
        imageView.setImage(image);

        setText(channel.getName());
        setGraphic(imageView);
      }
    }
  }
}
