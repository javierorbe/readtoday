package dev.team.readtoday.server.subscription.domain;

import java.util.Collection;
import java.util.Optional;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;

public interface SubscriptionRepository {

  /**
   * Saves a subscription to a channel
   * 
   * @param subscription to save
   */
  void save(Subscription subscription);

  /**
   * Deletes a subscription to a channel
   * 
   * @param subscription to delete
   */
  void remove(Subscription subscription);

  /**
   * Gets a collection of subscriptions from a certain user
   * 
   * @param userId id of the user
   * @returns if there are any returns a collection of subscriptions
   */
  Collection<Subscription> getAllByUserId(UserId userId);

  /**
   * Gets a specific subscription from a certain user
   * 
   * @param userId id of the user
   * @param idC id of the channel
   * @return if there is returns a subscription
   */
  Optional<Subscription> getFromId(UserId idU, ChannelId idC);
}
