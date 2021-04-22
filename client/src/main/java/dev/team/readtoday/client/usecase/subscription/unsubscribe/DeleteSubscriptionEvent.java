package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import dev.team.readtoday.client.model.Channel;

public final class DeleteSubscriptionEvent {

    private final Channel channel;

    public DeleteSubscriptionEvent(Channel channel) {
        this.channel = channel;
    }

    Channel getChannel() {
        return channel;
    }
}
