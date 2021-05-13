package dev.team.readtoday.server.customlist.infrastructure.controller.get;

import java.util.Collection;
import dev.team.readtoday.server.publication.domain.Publication;

public class CustomListGetPublicationsResponse {

  private final Collection<Publication> publications;

  public CustomListGetPublicationsResponse(Collection<Publication> publications) {
    this.publications = publications;
  }

  public Collection<Publication> getPublications() {
    return this.publications;
  }
}
