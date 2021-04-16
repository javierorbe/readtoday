package dev.team.readtoday.client.usecase.category.search.events;

public class SearchAllCategoriesFailedEvent {

  private final String reason;

  public SearchAllCategoriesFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
