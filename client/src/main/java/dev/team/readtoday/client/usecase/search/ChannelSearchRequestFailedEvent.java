package dev.team.readtoday.client.usecase.search;

public final class ChannelSearchRequestFailedEvent {

  private final String reason;

  ChannelSearchRequestFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
