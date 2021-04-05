package dev.team.readtoday.client.usercase.subscription;

public class SubscriptionRequest {

  private String userId;
  private String channelId;

  public SubscriptionRequest(String userId, String channelId) {
    this.userId = userId;
    this.channelId = channelId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getChannelId() {
    return this.channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }
}
