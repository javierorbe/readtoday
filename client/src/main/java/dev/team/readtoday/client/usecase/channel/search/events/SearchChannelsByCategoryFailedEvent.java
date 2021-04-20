package dev.team.readtoday.client.usecase.channel.search.events;

public final class SearchChannelsByCategoryFailedEvent {

  private final String reason;

  public SearchChannelsByCategoryFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
