package dev.team.readtoday.server.subscription.domain;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;

public enum SubscriptionMother {
  ;

  public static Subscription random() {
    return new Subscription(UserId.random(), ChannelId.random());
  }
}
