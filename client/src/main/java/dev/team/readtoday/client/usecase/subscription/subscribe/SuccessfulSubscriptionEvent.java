package dev.team.readtoday.client.usecase.subscription.subscribe;

public final class SuccessfulSubscriptionEvent {

  private final String channelId;

  SuccessfulSubscriptionEvent(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }
}
