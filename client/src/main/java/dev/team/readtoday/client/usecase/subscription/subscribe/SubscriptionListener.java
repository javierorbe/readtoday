package dev.team.readtoday.client.usecase.subscription.subscribe;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SubscriptionListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SubscriptionListener(EventBus eventBus, HttpRequestBuilder requestBuilder) {
    this.eventBus = eventBus;
    this.requestBuilder = requestBuilder;
  }

  @Subscribe
  public void onSubscriptionRequested(SubscriptionRequestedEvent event) {
    String channelId = event.getChannelId();

    LOGGER.trace("Subscription requested for channel: {}", channelId);

    SubscriptionRequest request = new SubscriptionRequest(channelId);
    HttpResponse response = requestBuilder.post(request);

    if (response.isStatusCreated()) {
      eventBus.post(new SuccessfulSubscriptionEvent(channelId));
    } else {
      eventBus.post(new SubscriptionFailedEvent(channelId, response.getStatusReason()));
    }
  }
}
