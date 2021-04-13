package dev.team.readtoday.server.subscription.domain;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Objects;

public final class Subscription {

  private final UserId userId;
  private final ChannelId channelId;

  public Subscription(UserId userId,
                      ChannelId channelId) {
    this.userId = Objects.requireNonNull(userId);
    this.channelId = Objects.requireNonNull(channelId);
  }

  public UserId getUserId() {
    return userId;
  }

  public ChannelId getChannelId() {
    return channelId;
  }
}
