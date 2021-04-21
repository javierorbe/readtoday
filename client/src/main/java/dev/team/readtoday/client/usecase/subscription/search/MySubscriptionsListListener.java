package dev.team.readtoday.client.usecase.subscription.search;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.subscription.search.events.MySubscriptionsListFailedEvent;
import dev.team.readtoday.client.usecase.subscription.search.events.MySubscriptionsListRequestedEvent;
import dev.team.readtoday.client.usecase.subscription.search.events.SuccessfulMySubscriptionsListEvent;
import dev.team.readtoday.client.usecase.subscription.search.messages.AllSubscriptionsResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SubscribedComponent
public final class MySubscriptionsListListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(MySubscriptionsListListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public MySubscriptionsListListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/subscriptions");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSubscriptionListRequested(MySubscriptionsListRequestedEvent event) {

    LOGGER.trace("Subscriptions list requested ");

    HttpResponse response = requestBuilder.get();

    if (response.isStatusOk()) {
      AllSubscriptionsResponse entity = response.getEntity(AllSubscriptionsResponse.class);
      ImmutableCollection<Channel> subscriptions = entity.toChannelsCollection();
      eventBus.post(new SuccessfulMySubscriptionsListEvent(subscriptions));

    } else {
      eventBus.post(new MySubscriptionsListFailedEvent(response.getStatusReason()));
    }
  }
}
