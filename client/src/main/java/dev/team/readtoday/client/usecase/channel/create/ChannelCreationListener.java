package dev.team.readtoday.client.usecase.channel.create;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelCreationEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelCreationFailedEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelSuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ChannelCreationListener class listens for channel creation events. When \ref
 * events.ChannelCreationEvent "ChannelCreationEvent" event is received, a POST HTTP request to
 * create a channel will be sent to readtoday server.
 * <p>
 * If channel is created successfully a \ref events.ChannelSuccessfullyCreatedEvent
 * "ChannelSuccessfullyCreatedEvent" event will be posted, otherwise an \ref
 * events.ChannelCreationFailedEvent "ChannelCreationFailedEvent" event will be posted by
 * eventbus.
 *
 * @author ikerrodriguez
 */
@SubscribedComponent
public final class ChannelCreationListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCreationListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  ChannelCreationListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/channels");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
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
