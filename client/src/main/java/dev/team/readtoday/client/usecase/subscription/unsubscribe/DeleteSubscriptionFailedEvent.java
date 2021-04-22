package dev.team.readtoday.client.usecase.subscription.unsubscribe;

public final class DeleteSubscriptionFailedEvent {

    private final String channelId;
    private final String reason;

    DeleteSubscriptionFailedEvent(String channelId, String reason) {
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
