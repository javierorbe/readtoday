package dev.team.readtoday.client.usecase.subscription.unsubscribe;

public class DeleteSubscriptionSuccessfulEvent {

    private final String channelId;

    DeleteSubscriptionSuccessfulEvent(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }

}
