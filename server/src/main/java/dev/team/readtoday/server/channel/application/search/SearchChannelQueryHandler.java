package dev.team.readtoday.server.channel.application.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.util.Collection;
import java.util.List;

@Service
public final class SearchChannelQueryHandler
    implements QueryHandler<SearchChannelQuery, SearchChannelQueryResponse> {

  private final SearchChannel searchChannel;

  public SearchChannelQueryHandler(SearchChannel searchChannel) {
    this.searchChannel = searchChannel;
  }

  @Override
  public SearchChannelQueryResponse handle(SearchChannelQuery query) {
    List<ChannelId> ids = query.getChannelIds().stream().map(ChannelId::fromString).toList();
    Collection<Channel> channels = searchChannel.apply(ids);
    return SearchChannelQueryResponse.fromDomainObject(channels);
  }
}
