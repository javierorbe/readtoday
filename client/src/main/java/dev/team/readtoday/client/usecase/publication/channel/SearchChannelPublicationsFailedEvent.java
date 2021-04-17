package dev.team.readtoday.client.usecase.publication.channel;

public final class SearchChannelPublicationsFailedEvent {

  private final String reason;

  SearchChannelPublicationsFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
