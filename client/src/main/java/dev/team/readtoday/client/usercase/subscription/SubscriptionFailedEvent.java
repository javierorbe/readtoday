package dev.team.readtoday.client.usercase.subscription;

public final class SubscriptionFailedEvent {

  private final String channelId;
  private final String reason;

  public SubscriptionFailedEvent(String channelId, String reason) {
    this.channelId = channelId;
    this.reason = reason;
  }

  public String getChannelId() {
    return channelId;
  }

  public String getReason() {
    return reason;
  }
}
