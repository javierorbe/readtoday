package dev.team.readtoday.server.subscription.domain;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.user.domain.UserId;
import java.util.Objects;

public final class Subscription {

  private final UserId idUser;
  private final ChannelId idChannel;

  public Subscription(UserId idUser,
                      ChannelId idChannel) {
    Objects.requireNonNull(idUser);
    Objects.requireNonNull(idChannel);

    this.idUser = idUser;
    this.idChannel = idChannel;
  }

  public UserId getIdUser() {
    return idUser;
  }

  public ChannelId getIdChannel() {
    return idChannel;
  }
}
