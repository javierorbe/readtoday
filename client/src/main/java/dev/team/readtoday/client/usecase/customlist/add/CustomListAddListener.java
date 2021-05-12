package dev.team.readtoday.client.usecase.customlist.add;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.customlist.add.events.CustomListAddEvent;
import dev.team.readtoday.client.usecase.customlist.add.events.CustomListAddFailedEvent;
import dev.team.readtoday.client.usecase.customlist.add.events.CustomListAddSuccesfulEvent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

@SubscribedComponent
public final class CustomListAddListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomListAddListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public CustomListAddListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    this.requestBuilder = factory.buildWithAuth("custom-list/add");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onCustomListAddRequestReceived(CustomListAddEvent event) {
    LOGGER.trace("Sended publication add to custom list request.");
    HttpResponse response = requestBuilder.post(event.getRequest());
    if (response.isStatusOk()) {
      LOGGER.trace("Publication added successfully.");
      eventBus.post(new CustomListAddSuccesfulEvent());
    } else {
      LOGGER.trace("Unable to add publication");
      eventBus.post(new CustomListAddFailedEvent(response.getStatusReason()));
    }
  }
}
