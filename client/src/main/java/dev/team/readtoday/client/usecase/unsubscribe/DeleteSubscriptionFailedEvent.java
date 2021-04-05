package dev.team.readtoday.client.usecase.unsubscribe;

public class DeleteSubscriptionFailedEvent {

  private final String channelId;
  private final String reason;

  public DeleteSubscriptionFailedEvent(String channelId, String reason) {
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
