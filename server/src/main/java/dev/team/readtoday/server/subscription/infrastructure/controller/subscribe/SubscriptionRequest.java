package dev.team.readtoday.server.subscription.infrastructure.controller.subscribe;

public class SubscriptionRequest {

  private String channelId;

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  String getChannelId() {
    return channelId;
  }
}
