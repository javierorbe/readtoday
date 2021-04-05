package dev.team.readtoday.client.usercase.subscription;

public class SubscriptionRequest {

  private final String channelId;

  public SubscriptionRequest(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }
}
