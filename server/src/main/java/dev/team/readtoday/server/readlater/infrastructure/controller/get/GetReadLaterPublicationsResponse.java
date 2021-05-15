package dev.team.readtoday.server.readlater.infrastructure.controller.get;

import java.util.Collection;
import dev.team.readtoday.server.publication.application.search.PublicationResponse;

public class GetReadLaterPublicationsResponse {

  private final Collection<PublicationResponse> publications;

  public GetReadLaterPublicationsResponse(Collection<PublicationResponse> publications) {
    this.publications = publications;
  }

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }
}
