package dev.team.readtoday.server.subscription.infrastructure.controller.post;

public final class TestSubscriptionPostRequest {

  private final String channelId;

  TestSubscriptionPostRequest(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }
}
