package dev.team.readtoday.client.usecase.customlist.create;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreatedSuccessfullyEvent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreationEvent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreationFailedEvent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SubscribedComponent
public final class CustomListCreationListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomListCreationListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public CustomListCreationListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    this.requestBuilder = factory.buildWithAuth("/custom-list");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onCustomListCreationRequestReceived(CustomListCreationEvent event) {
    LOGGER.trace("Sending custom list creation request");
    HttpResponse response = requestBuilder.post(event.getRequest());
    if (response.isStatusOk()) {
      LOGGER.trace("Custom list created successfully");
      eventBus.post(new CustomListCreatedSuccessfullyEvent());
    } else {
      LOGGER.trace("Custom list was not created.");
      eventBus.post(new CustomListCreationFailedEvent(response.getStatusReason()));
    }
  }
}
