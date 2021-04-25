package dev.team.readtoday.client.usecase.channel.edit.events;

public final class ChannelEditionFailed {

  private final String reason;

  public ChannelEditionFailed(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
