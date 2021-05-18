package dev.team.readtoday.server.channel.application.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import java.util.Collection;
import java.util.Collections;

public final class SearchChannelQueryResponse implements QueryResponse {

  private final Collection<ChannelResponse> channels;

  private SearchChannelQueryResponse(Collection<ChannelResponse> channels) {
    this.channels = Collections.unmodifiableCollection(channels);
  }

  public Collection<ChannelResponse> getChannels() {
    return channels;
  }

  public static SearchChannelQueryResponse fromDomainObject(Collection<Channel> channels) {
    var serializedChannels =
        channels.stream().map(ChannelResponse::fromDomainObject).toList();
    return new SearchChannelQueryResponse(serializedChannels);
  }
}
