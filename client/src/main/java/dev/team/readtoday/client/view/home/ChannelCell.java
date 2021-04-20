package dev.team.readtoday.client.view.home;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.publication.channel.ChannelPublicationsFoundEvent;
import dev.team.readtoday.client.usecase.publication.channel.SearchChannelPublicationsEvent;
import dev.team.readtoday.client.usecase.publication.channel.SearchChannelPublicationsFailedEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class ChannelCell extends ListCell<Channel> {

  private static final double CHANNEL_FAVICON_FIT_HEIGHT = 16.0;

  private static final Cache<Channel, Image> CHANNEL_IMAGE_CACHE =
      CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES).build();

  private final ImageView imageView = new ImageView();
  private final EventBus eventBus;

  ChannelCell(EventBus eventBus) {
    this.eventBus = eventBus;

    imageView.setPreserveRatio(true);
    imageView.setFitHeight(CHANNEL_FAVICON_FIT_HEIGHT);

    setOnMouseClicked(this::handleMouseClickEvent);

    eventBus.register(this);
  }

  private boolean hasNonNullItemProperty() {
    return itemProperty().isNotNull().get();
  }

  private void handleMouseClickEvent(MouseEvent event) {
    if (hasNonNullItemProperty()) {
      if (event.getButton().equals(MouseButton.PRIMARY) && (event.getClickCount() == 2)) {
        openChannelPublicationWindow();
      }
    }
  }

  private void openChannelPublicationWindow() {
    String channelId = getItem().getId();
    eventBus.post(new SearchChannelPublicationsEvent(channelId));
  }

  @Subscribe
  public void onChannelPublicationsFoundEvent(ChannelPublicationsFoundEvent event) {
    if (hasNonNullItemProperty()) {
      Channel channel = getItem();
      if (event.getChannelId().equals(channel.getId())) {
        PublicationListWindow.open(eventBus, channel, event.getPublications());
      }
    }
  }

  @Subscribe
  public void onSearchChannelPublicationsFailed(SearchChannelPublicationsFailedEvent event) {
    AlertLauncher.error("Could not find channel publications", event.getReason());
  }

  @Override
  protected void updateItem(Channel channel, boolean empty) {
    super.updateItem(channel, empty);

    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      Image image = getChannelImage(channel);
      imageView.setImage(image);
      setGraphic(imageView);
      setText(channel.getName());
    }
  }

  private static Image getChannelImage(Channel channel) {
    try {
      return CHANNEL_IMAGE_CACHE.get(channel, () -> new Image(channel.getFaviconImageUrl()));
    } catch (ExecutionException e) {
      throw new IllegalStateException("Could not load image from cache.", e);
    }
  }
}
