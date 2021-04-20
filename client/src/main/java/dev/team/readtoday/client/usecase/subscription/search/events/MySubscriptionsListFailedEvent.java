package dev.team.readtoday.client.usecase.subscription.search.events;

public final class MySubscriptionsListFailedEvent {
  private final String reason;

  public MySubscriptionsListFailedEvent(String reason) {
      this.reason = reason;
  }
  public String getReason() {
        return reason;
    }
}
