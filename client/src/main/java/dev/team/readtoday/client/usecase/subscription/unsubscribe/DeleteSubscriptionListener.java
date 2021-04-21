package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SubscribedComponent
public final class DeleteSubscriptionListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSubscriptionListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  DeleteSubscriptionListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/unsubscribe");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onDeleteSubscription(DeleteSubscriptionEvent event) {
    String channelId = event.getChannelId();
    LOGGER.trace("Unsubscribe requested for channel: {}", channelId);
    HttpResponse response = requestBuilder.withParam("channelId", channelId).delete(channelId);
    // HttpResponse response = requestBuilder.delete(event.getChannelId());

    if (response.isStatusNoContent()) {
      eventBus.post(new DeleteSubscriptionSuccessfulEvent(channelId));
    } else {
      eventBus.post(new DeleteSubscriptionFailedEvent(channelId, response.getStatusReason()));
    }
  }
}
