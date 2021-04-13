package dev.team.readtoday.client.usecase.subscription.unsubscribe;

public final class DeleteSubscriptionEvent {

  private final String channelId;

  public DeleteSubscriptionEvent(String channelId) {
    this.channelId = channelId;
  }

  String getChannelId() {
    return channelId;
  }
}
