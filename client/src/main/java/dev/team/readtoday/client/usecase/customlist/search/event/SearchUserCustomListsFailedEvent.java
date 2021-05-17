package dev.team.readtoday.client.usecase.customlist.search.event;

public class SearchUserCustomListsFailedEvent {
  private final String reason;

  public SearchUserCustomListsFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
