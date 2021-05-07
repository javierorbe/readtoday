package dev.team.readtoday.server.publication.application.formatted;

import dev.team.readtoday.server.publication.application.FormattedPublicationsResponse;
import dev.team.readtoday.server.shared.domain.bus.query.Query;
import java.util.Collections;
import java.util.List;

public final class FormattedTopPublicationsQuery
    implements Query<FormattedPublicationsResponse> {

  private final List<String> channelIds;

  public FormattedTopPublicationsQuery(List<String> channelIds) {
    this.channelIds = Collections.unmodifiableList(channelIds);
  }

  public List<String> getChannelIds() {
    return channelIds;
  }
}
