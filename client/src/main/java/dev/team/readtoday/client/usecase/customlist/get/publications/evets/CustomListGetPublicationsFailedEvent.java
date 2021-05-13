package dev.team.readtoday.client.usecase.customlist.get.publications.evets;

public class CustomListGetPublicationsFailedEvent {

  private final String reason;

  public CustomListGetPublicationsFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
