package dev.team.readtoday.client.view.home;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.team.readtoday.client.model.Channel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ChannelCell extends ListCell<Channel> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCell.class);

  private static final double CHANNEL_FAVICON_FIT_HEIGHT = 16.0;

  private static final Cache<Channel, Image> CHANNEL_IMAGE_CACHE =
      CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES).build();

  private final ImageView imageView = new ImageView();

  ChannelCell() {
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
      try {
        Image image =
            CHANNEL_IMAGE_CACHE.get(channel, () -> new Image(channel.getFaviconImageUrl()));
        imageView.setImage(image);
        setGraphic(imageView);
      } catch (ExecutionException e) {
        LOGGER.error("Could not load image from cache.", e);
      }

      setText(channel.getName());
    }
  }
}
