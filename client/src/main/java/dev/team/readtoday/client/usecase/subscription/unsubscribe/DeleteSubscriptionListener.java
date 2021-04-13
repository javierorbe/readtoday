package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.ThreadMode;

@SubscribedComponent
public final class DeleteSubscriptionListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  DeleteSubscriptionListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/subscriptions");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
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
