package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

public class DeleteSubscriptionListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public DeleteSubscriptionListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/subscriptions");
    eventBus.register(this);
  }

  @Subscribe
  public void onDeleteSubscription(DeleteSubscriptionEvent event) {
    HttpResponse response = requestBuilder.delete(event.getChannelId());

    if (response.isStatusNoContent()) {
      eventBus.post(new DeleteSubscriptionSuccessfulEvent());
    } else {
      eventBus.post(
          new DeleteSubscriptionFailedEvent(event.getChannelId(), response.getStatusReason())
      );
    }
  }
}
