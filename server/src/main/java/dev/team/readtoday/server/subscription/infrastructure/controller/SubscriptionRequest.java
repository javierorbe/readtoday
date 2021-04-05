package dev.team.readtoday.server.subscription.infrastructure.controller;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.user.domain.UserId;

public class SubscriptionRequest {

  private String idUser;
  private String idChannel;

  public SubscriptionRequest(String idUser, String idChannel) {
    this.idUser = idUser;
    this.idChannel = idChannel;
  }

  public String getUserId() {
    return this.idUser;
  }

  public void setUserId(String idUser) {
    this.idUser = idUser;
  }

  public String getChannelId() {
    return this.idChannel;
  }

  public void setChannelId(String idChannel) {
    this.idChannel = idChannel;
  }

  public Subscription toSubscription() {
    return new Subscription(UserId.fromString(idUser), ChannelId.fromString(idChannel));

  }
}
