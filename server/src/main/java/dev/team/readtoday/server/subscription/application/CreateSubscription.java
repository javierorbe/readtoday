package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;

public class CreateSubscription {

  private final SubscriptionRepository subscriptionRepository;

  public CreateSubscription(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  public void createSubscription(Subscription subscription) {
    subscriptionRepository.save(subscription);
  }
}
