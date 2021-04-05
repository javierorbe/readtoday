package dev.team.readtoday.client.usercase.subscription;

public final class SuccessfulSubscriptionEvent {

  private final String channelId;

  SuccessfulSubscriptionEvent(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }
}
