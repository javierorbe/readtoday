package dev.team.readtoday.client.usecase.subscription.subscribe;

public final class SubscriptionFailedEvent {

    private final String channelId;
    private final String reason;

    SubscriptionFailedEvent(String channelId, String reason) {
        this.channelId = channelId;
        this.reason = reason;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getReason() {
        return reason;
    }
}
