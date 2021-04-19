package dev.team.readtoday.server.channel.application.search;

import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.server.shared.domain.bus.query.Query;
import java.util.Collection;

public final class SearchChannelQuery implements Query<SearchChannelQueryResponse> {

  private final Collection<String> channelIds;

  public SearchChannelQuery(Collection<String> channelIds) {
    this.channelIds = ImmutableSet.copyOf(channelIds);
  }

  Collection<String> getChannelIds() {
    return channelIds;
  }
}
