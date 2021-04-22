package dev.team.readtoday.server.publication.application.findforchannel;

import dev.team.readtoday.server.shared.domain.bus.query.Query;
import java.util.Collection;
import java.util.Collections;

public class FindRelevantPublicationsQuery implements Query<FindRelevantPublicationsResponse> {

  private final Collection<String> channelIds;

  public FindRelevantPublicationsQuery(Collection<String> channelIds) {
    this.channelIds = Collections.unmodifiableCollection(channelIds);
  }

  Collection<String> getChannelIds() {
    return channelIds;
  }
}
