package dev.team.readtoday.client.usecase.channel.create;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChannelCreationListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCreationListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public ChannelCreationListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/channels");
    eventBus.register(this);
  }

  @Subscribe
  public void onChannelCreationRequestReceived(ChannelCreationEvent event) {
    LOGGER.trace("Sending channel creation request.");
    HttpResponse response = requestBuilder.post(event.getRequest());
    if (response.isStatusCreated()) {
      eventBus.post(new ChannelSuccessfullyCreatedEvent());
    } else {
      eventBus.post(new ChannelCreationFailedEvent(response.getStatusReason()));
    }
  }
}
