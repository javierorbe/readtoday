package dev.team.readtoday.client.usecase.unsubscribe;

public class DeleteSubscriptionEvent {

  private final String channelId;

  public DeleteSubscriptionEvent(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }

}
