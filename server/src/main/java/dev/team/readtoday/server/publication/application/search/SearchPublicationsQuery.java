package dev.team.readtoday.server.publication.application.search;

import dev.team.readtoday.server.shared.domain.bus.query.Query;
import java.util.Collection;
import java.util.Collections;

public final class SearchPublicationsQuery implements Query<SearchPublicationsResponse> {

  private final Collection<String> channelIds;

  public SearchPublicationsQuery(Collection<String> channelIds) {
    this.channelIds = Collections.unmodifiableCollection(channelIds);
  }

  Collection<String> getChannelIds() {
    return channelIds;
  }
}
