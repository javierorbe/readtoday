package dev.team.readtoday.client.usecase.subscription;

public final class SubscriptionRequestedEvent {

  private final String channelId;

  public SubscriptionRequestedEvent(String channelId) {
    this.channelId = channelId;
  }

  String getChannelId() {
    return channelId;
  }
}
