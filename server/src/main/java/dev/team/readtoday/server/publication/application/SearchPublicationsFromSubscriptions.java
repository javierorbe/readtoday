package dev.team.readtoday.server.publication.application;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.RssFeedException;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.application.GetUserSubscriptions;
import dev.team.readtoday.server.subscription.domain.Subscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class SearchPublicationsFromSubscriptions {

    private final GetUserSubscriptions getUsersubscriptions;
    private final SearchPublications searchPublications;

    public SearchPublicationsFromSubscriptions(GetUserSubscriptions getUsersubscriptions,
                                               SearchPublications searchPublications){
        this.getUsersubscriptions = getUsersubscriptions;
        this.searchPublications = searchPublications;
    }
    public Collection<Publication> search(UserId userId) throws RssFeedException {
        List<Publication> publications = new ArrayList<>();
        Collection<Subscription> subscriptions = getUsersubscriptions.search(userId);
        Iterator<Subscription> iterator = subscriptions.iterator();

        while(iterator.hasNext()){
            publications.addAll(searchPublications.search(iterator.next().getChannelId()));
        }

        return publications;
    }
}
