package dev.team.readtoday.server.subscription.infrastructure.controller.publications;

import dev.team.readtoday.server.publication.application.search.PublicationResponse;
import java.util.Collection;

public final class PublicationListResponse {

  private final Collection<PublicationResponse> publications;

  PublicationListResponse(Collection<PublicationResponse> publications) {
    this.publications = publications;
  }

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }
}
