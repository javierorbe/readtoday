package dev.team.readtoday.client.usecase.readlater;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.model.Publication;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.subscription.search.MySubscriptionsListListener;
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
public class SaveReadLaterListListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(SaveReadLaterListListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SaveReadLaterListListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/readlater");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onReadLaterListRequested(SaveReadLaterListRequestedEvent event) {
    LOGGER.trace("Subscriptions list requested ");

    HttpResponse response = requestBuilder.post(event.getRequest());

    if (response.isStatusOk()) {

      eventBus.post(new SuccessfulSaveReadLaterListEvent());

    } else {
      eventBus.post(new SaveReadLaterListFailedEvent(response.getStatusReason()));
    }
  }

}

