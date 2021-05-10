package dev.team.readtoday.client.usecase.customlist.create.events;

public final class CustomListCreationFailedEvent {

  private final String reason;

  public CustomListCreationFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
