package dev.team.readtoday.server.readlater.infrastructure.controller.get;

import java.util.Collection;
import dev.team.readtoday.server.publication.domain.Publication;

public class GetReadLaterPublicationsResponse {

  private Collection<Publication> publications;

  public GetReadLaterPublicationsResponse(Collection<Publication> publications) {
    this.publications = publications;
  }

  public Collection<Publication> getPublications() {
    return this.publications;
  }
}
