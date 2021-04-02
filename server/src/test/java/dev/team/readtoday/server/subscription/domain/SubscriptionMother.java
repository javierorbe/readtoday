package dev.team.readtoday.server.subscription.domain;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.user.domain.UserId;

public enum SubscriptionMother {
  ;

  public static Subscription random() {
    return new Subscription(UserId.random(), ChannelId.random());
  }
}
