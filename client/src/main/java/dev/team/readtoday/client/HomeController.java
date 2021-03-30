package dev.team.readtoday.client;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class HomeController implements Initializable {

  private static final double CHANNEL_FAVICON_FIT_HEIGHT = 16.0;

  private final Map<Channel, Image> channelImageCache = new HashMap<>();

  @FXML
  private ListView<Channel> channelListView;
  private final ObservableList<Channel> subscribedChannels;

  public HomeController(Collection<Channel> subscribedChannels) {
    this.subscribedChannels = FXCollections.observableArrayList(subscribedChannels);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Objects.requireNonNull(channelListView);

    channelListView.setCellFactory(listView -> new ListCell<>() {
      private final ImageView imageView = new ImageView();

      @Override
      protected void updateItem(Channel channel, boolean empty) {
        super.updateItem(channel, empty);

        if (empty) {
          imageView.setImage(null);

          setText(null);
          setGraphic(null);
        } else {
          Image image = channelImageCache.computeIfAbsent(channel,
              ignored -> new Image(channel.getFaviconImageUrl()));

          imageView.setImage(image);
          imageView.setPreserveRatio(true);
          imageView.setFitHeight(CHANNEL_FAVICON_FIT_HEIGHT);

          setText(channel.getName());
          setGraphic(imageView);
        }
      }
    });
    channelListView.setItems(subscribedChannels);
  }
}
