package dev.team.readtoday.client.usecase.channel.edit;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.channel.edit.events.ChannelEditedSuccessfully;
import dev.team.readtoday.client.usecase.channel.edit.events.ChannelEditionFailed;
import dev.team.readtoday.client.usecase.channel.edit.events.EditChannelEvent;
import dev.team.readtoday.client.usecase.channel.edit.messages.EditChannelRequest;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SubscribedComponent
public final class ChannelEditListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEditListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public ChannelEditListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    this.requestBuilder = factory.buildWithAuth("/channels");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onChannelEdit(EditChannelEvent event) {
    String channelId = event.getChannelId();
    EditChannelRequest request = event.getRequest();

    LOGGER.trace("Sending edit channel request to id {}.", channelId);

    // PUT Http request to '/channels/id'
    HttpResponse response = requestBuilder.put(channelId, request);

    if (response.isStatusOk()) {
      LOGGER.trace("Channel was edited successfully");
      eventBus.post(new ChannelEditedSuccessfully());
    } else {
      LOGGER.trace("Channel edition failed");
      eventBus.post(new ChannelEditionFailed(response.getStatusReason()));
    }
  }
}
