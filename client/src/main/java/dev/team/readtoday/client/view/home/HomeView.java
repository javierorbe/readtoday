package dev.team.readtoday.client.view.home;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
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

  private final ImmutableList<Channel> allChannels;
  private final Map<Category, List<Channel>> categoryToChannel;

  private final SearchChannelController searchChannelController;

  @FXML
  private TextField channelsByCategory;

  public HomeView(Collection<Channel> subscribedChannels,
      SearchChannelController searchChannelController) {
    allChannels = ImmutableList.sortedCopyOf(subscribedChannels);
    this.searchChannelController = searchChannelController;
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

  public void unselectCategory() {
    channelCategorySelector.valueProperty().set(null);
  }

  public void searchNewChannelsByCategory() {
    List<Channel> newChannels = searchChannelController.searchNewChannelsByCategoryName(
            channelsByCategory.getText());

    newChannelListView.setItems(FXCollections.observableArrayList(newChannels));
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
