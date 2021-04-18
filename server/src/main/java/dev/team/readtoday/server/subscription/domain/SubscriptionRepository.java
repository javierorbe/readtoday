package dev.team.readtoday.server.subscription.domain;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

  void save(Subscription subscription);

  void remove(Subscription subscription);

  Collection<Subscription> getAllByUserId(UserId userId);

  Optional<Subscription> getFromId(UserId idU, ChannelId idC);
}
