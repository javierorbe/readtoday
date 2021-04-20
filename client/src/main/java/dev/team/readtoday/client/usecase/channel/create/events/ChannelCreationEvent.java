package dev.team.readtoday.client.usecase.channel.create.events;

import dev.team.readtoday.client.usecase.channel.create.messages.ChannelCreationRequest;

public final class ChannelCreationEvent {

  private final ChannelCreationRequest request;

  public ChannelCreationEvent(ChannelCreationRequest request) {
    this.request = request;
  }

  public ChannelCreationRequest getRequest() {
    return request;
  }
}
