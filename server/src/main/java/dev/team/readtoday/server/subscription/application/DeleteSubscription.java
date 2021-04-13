package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.shared.domain.UserId;

public class DeleteSubscription {

  private final SubscriptionRepository repository;

  public DeleteSubscription(SubscriptionRepository repository) {
    this.repository = repository;
  }

  public void delete(UserId userId, ChannelId channelId) {
    Subscription subscription = new Subscription(userId, channelId);
    repository.remove(subscription);
  }
}
