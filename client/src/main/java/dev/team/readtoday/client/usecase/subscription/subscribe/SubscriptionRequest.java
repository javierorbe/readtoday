package dev.team.readtoday.client.usecase.subscription.subscribe;

public final class SubscriptionRequest {

    private final String channelId;

    SubscriptionRequest(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }
}
