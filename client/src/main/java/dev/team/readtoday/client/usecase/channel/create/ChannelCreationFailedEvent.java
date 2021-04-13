package dev.team.readtoday.client.usecase.channel.create;

public final class ChannelCreationFailedEvent {

  private final String reason;

  ChannelCreationFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
