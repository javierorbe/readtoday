package dev.team.readtoday.client.usecase.subscription.search.events;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.model.Channel;

public class SuccessfulMySubscriptionsListEvent {
  private final ImmutableCollection<Channel> subscriptions;

  public SuccessfulMySubscriptionsListEvent(ImmutableCollection<Channel> subscriptions) {
    this.subscriptions = subscriptions;
  }

  public ImmutableCollection<Channel> getSubscriptions() {
    return subscriptions;
  }
}
