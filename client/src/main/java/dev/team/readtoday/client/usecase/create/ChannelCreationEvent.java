package dev.team.readtoday.client.usecase.create;

public final class ChannelCreationEvent {

  private final ChannelCreationRequest request;

  public ChannelCreationEvent(ChannelCreationRequest request) {
    this.request = request;
  }

  ChannelCreationRequest getRequest() {
    return request;
  }
}
