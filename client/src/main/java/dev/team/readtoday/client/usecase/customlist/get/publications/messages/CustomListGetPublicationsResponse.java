package dev.team.readtoday.client.usecase.customlist.get.publications.messages;

import java.util.Collection;
import dev.team.readtoday.client.model.Publication;

public class CustomListGetPublicationsResponse {

  private final Collection<Publication> publications;

  public CustomListGetPublicationsResponse(Collection<Publication> publications) {
    this.publications = publications;
  }

  public Collection<Publication> getPublications() {
    return this.publications;
  }
}
