package dev.team.readtoday.client.usecase.channel.create.events;

public final class ChannelCreationFailedEvent {

  private final String reason;

  public ChannelCreationFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
