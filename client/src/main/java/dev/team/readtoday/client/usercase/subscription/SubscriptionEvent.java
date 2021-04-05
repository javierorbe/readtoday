package dev.team.readtoday.client.usercase.subscription;

public final class SubscriptionEvent {

  private final SubscriptionRequest request;

  public SubscriptionEvent(SubscriptionRequest request) {
    this.request = request;
  }

  public SubscriptionRequest getRequest() {
    return this.request;
  }
}
