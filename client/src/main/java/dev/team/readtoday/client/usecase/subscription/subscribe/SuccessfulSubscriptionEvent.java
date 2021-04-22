package dev.team.readtoday.client.usecase.subscription.subscribe;

import dev.team.readtoday.client.model.Channel;

public final class SuccessfulSubscriptionEvent {

    private final Channel channel;

    SuccessfulSubscriptionEvent(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}
