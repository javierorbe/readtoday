package dev.team.readtoday.client.usecase.subscription.subscribe;


import dev.team.readtoday.client.model.Channel;

public final class SubscriptionRequestedEvent {

  private final Channel channel;

  public SubscriptionRequestedEvent(Channel channel) {
    this.channel = channel;
  }

  Channel getChannel() {
    return channel;
  }
}
