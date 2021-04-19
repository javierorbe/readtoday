package dev.team.readtoday.server.publication.application.search;

import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import java.util.Collection;
import java.util.Collections;

public final class SearchPublicationsResponse implements QueryResponse {

  private final Collection<PublicationResponse> publications;

  SearchPublicationsResponse(Collection<PublicationResponse> publications) {
    this.publications = Collections.unmodifiableCollection(publications);
  }

  public Collection<PublicationResponse> getPublications() {
    return publications;
  }
}
