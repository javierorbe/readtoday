package dev.team.readtoday.server.subscription.domain;

import java.util.List;
import java.util.Optional;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.user.domain.UserId;


public interface SubscriptionRepository {

  void save(Subscription subscription);

  void remove(Subscription subscription);

  Optional<List<Subscription>> getAllByUserId(UserId userId);

  Optional<Subscription> getFromId(UserId idU, ChannelId idC);

}
