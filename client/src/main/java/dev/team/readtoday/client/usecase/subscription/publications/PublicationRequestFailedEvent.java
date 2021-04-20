package dev.team.readtoday.client.usecase.subscription.publications;

public class PublicationRequestFailedEvent {

  private String reason;

  public PublicationRequestFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return this.reason;
  }
}
