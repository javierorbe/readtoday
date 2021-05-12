package dev.team.readtoday.client.usecase.customlist.add.events;

public class CustomListAddFailedEvent {

  private final String reason;

  public CustomListAddFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return this.reason;
  }
}
