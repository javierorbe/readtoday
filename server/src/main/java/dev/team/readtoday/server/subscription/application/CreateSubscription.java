package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.user.domain.UserId;

public final class CreateSubscription {

  private final SubscriptionRepository repository;

  public CreateSubscription(SubscriptionRepository repository) {
    this.repository = repository;
  }

  public void create(UserId userId, ChannelId channelId) {
    Subscription subscription = new Subscription(userId, channelId);
    repository.save(subscription);
  }
}
