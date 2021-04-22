package dev.team.readtoday.client.usecase.subscription.subscribe;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SubscribedComponent
public final class SubscriptionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionListener.class);

    private final EventBus eventBus;
    private final HttpRequestBuilder requestBuilder;

    SubscriptionListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
        this.eventBus = eventBus;
        requestBuilder = factory.buildWithAuth("/subscribe");
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onSubscriptionRequested(SubscriptionRequestedEvent event) {
        String channelId = event.getChannel().getId();

        LOGGER.trace("Subscription requested for channel: {}", channelId);

        HttpResponse response = requestBuilder.withParam("channelId", channelId).post(channelId);

        if (response.isStatusCreated()) {
            eventBus.post(new SuccessfulSubscriptionEvent(event.getChannel()));
        } else {
            eventBus.post(new SubscriptionFailedEvent(channelId, response.getStatusReason()));
        }
    }
}
