package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;

import java.util.List;
import java.util.Optional;

public class GetUserSubscriptions {

    private final SubscriptionRepository repository;
    private final SearchUserById searchUserById;

    public GetUserSubscriptions(SubscriptionRepository repository, SearchUserById searchUserById){
        this.repository = repository;
        this.searchUserById = searchUserById;
    }

    public Optional<List<Subscription>>search(UserId userId){
        User user = searchUserById.search(userId);
        return repository.getAllByUserId(user.getId());
    }
}
