package dev.team.readtoday.client.usecase.category.create.events;

public final class CategoryCreationFailedEvent {

  private final String reason;

  public CategoryCreationFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
