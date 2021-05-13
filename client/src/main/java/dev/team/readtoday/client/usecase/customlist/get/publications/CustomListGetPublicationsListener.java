package dev.team.readtoday.client.usecase.customlist.get.publications;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.customlist.get.publications.evets.CustomListGetPublicationsEvent;
import dev.team.readtoday.client.usecase.customlist.get.publications.evets.CustomListGetPublicationsFailedEvent;
import dev.team.readtoday.client.usecase.customlist.get.publications.evets.CustomListGetPublicationsSuccessfulEvent;
import dev.team.readtoday.client.usecase.customlist.get.publications.messages.CustomListGetPublicationsResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

@SubscribedComponent
public class CustomListGetPublicationsListener {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomListGetPublicationsListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public CustomListGetPublicationsListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    this.requestBuilder = factory.buildWithAuth("custom-list/get");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onCustomListGetPublicationRequest(CustomListGetPublicationsEvent event) {
    LOGGER.trace("Sending custom list get publications request");
    HttpResponse response = requestBuilder.post(event.getRequest());
    if (response.isStatusOk()) {
      LOGGER.trace("Publications obtained successfully");
      var entity = response.getEntity(CustomListGetPublicationsResponse.class);
      eventBus.post(new CustomListGetPublicationsSuccessfulEvent(entity.getPublications()));
    } else {
      LOGGER.trace("Publications were not obtained.");
      eventBus.post(new CustomListGetPublicationsFailedEvent(response.getStatusReason()));
    }
  }
}
