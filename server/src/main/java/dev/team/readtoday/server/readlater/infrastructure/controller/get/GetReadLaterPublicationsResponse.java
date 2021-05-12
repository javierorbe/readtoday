package dev.team.readtoday.server.readlater.infrastructure.controller.get;

import dev.team.readtoday.server.publication.domain.Publication;
import java.util.Collection;

public class GetReadLaterPublicationsResponse {

  private final Collection<Publication> publications;

  public GetReadLaterPublicationsResponse(Collection<Publication> publications) {
    this.publications = publications;
  }

  public Collection<Publication> getPublications() {
    return this.publications;
  }
}
