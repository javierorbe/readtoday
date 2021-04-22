package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;

@Service
public final class CreateSubscription {

    private final SubscriptionRepository repository;

    public CreateSubscription(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public void create(UserId userId, ChannelId channelId) {
        Subscription subscription = new Subscription(userId, channelId);
        repository.save(subscription);
    }
}
