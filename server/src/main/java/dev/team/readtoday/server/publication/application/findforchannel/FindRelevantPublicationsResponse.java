package dev.team.readtoday.server.publication.application.findforchannel;

import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import java.util.Collection;
import java.util.Collections;

public final class FindRelevantPublicationsResponse implements QueryResponse {

  private final Collection<PublicationResponse> publications;

  FindRelevantPublicationsResponse(Collection<PublicationResponse> publications) {
    this.publications = Collections.unmodifiableCollection(publications);
  }

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }
}
