package dev.team.readtoday.client.usecase.channel.edit.events;

import dev.team.readtoday.client.usecase.channel.edit.messages.EditChannelRequest;

public class EditChannelEvent {

  private final String channelId;
  private final EditChannelRequest request;

  public EditChannelEvent(String channelId,
      EditChannelRequest request) {
    this.channelId = channelId;
    this.request = request;
  }

  public String getChannelId() {
    return channelId;
  }

  public EditChannelRequest getRequest() {
    return request;
  }
}
