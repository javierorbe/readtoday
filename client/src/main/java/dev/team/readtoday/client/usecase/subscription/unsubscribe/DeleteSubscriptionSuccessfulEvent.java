package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import dev.team.readtoday.client.model.Channel;

public class DeleteSubscriptionSuccessfulEvent {

    private final Channel channel;

    DeleteSubscriptionSuccessfulEvent(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

}
