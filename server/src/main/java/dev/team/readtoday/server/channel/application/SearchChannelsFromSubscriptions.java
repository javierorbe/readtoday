package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.channel.application.search.SearchChannel;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.application.GetUserSubscriptions;
import dev.team.readtoday.server.subscription.domain.Subscription;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SearchChannelsFromSubscriptions {

    private final GetUserSubscriptions getUserSubscriptions;
    private final SearchChannel searchChannel;

    public SearchChannelsFromSubscriptions(GetUserSubscriptions getUserSubscriptions,
                                           SearchChannel searchChannel) {
        this.searchChannel = searchChannel;
        this.getUserSubscriptions = getUserSubscriptions;
    }

    public Collection<Channel> search (UserId userId){
        Collection<Subscription> subscriptions = getUserSubscriptions.search(userId);
        List<ChannelId> channelsId = new ArrayList<>();
        Iterator<Subscription> iterator = subscriptions.iterator();

        while(iterator.hasNext()){
            channelsId.add(iterator.next().getChannelId());
        }
        return searchChannel.apply(channelsId);

    }
}
