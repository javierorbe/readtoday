package dev.team.readtoday.server.subscription.domain;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.user.domain.UserId;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

  void save(Subscription subscription);

  void remove(Subscription subscription);

  Optional<List<Subscription>> getAllByUserId(UserId userId);

  Optional<Subscription> getFromId(UserId idU, ChannelId idC);
}