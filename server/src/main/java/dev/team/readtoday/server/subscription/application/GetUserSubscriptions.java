package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;

import java.util.Collection;

@Service
public class GetUserSubscriptions {

    private final SubscriptionRepository repository;

    public GetUserSubscriptions(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Collection<Subscription> search(UserId userId) {
        return repository.getAllByUserId(userId);
    }
}
