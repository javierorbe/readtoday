package dev.team.readtoday.client.usecase.customlist.get.publications.evets;

import java.util.Collection;
import dev.team.readtoday.client.model.Publication;

public class CustomListGetPublicationsSuccessfulEvent {

  private final Collection<Publication> publications;

  public CustomListGetPublicationsSuccessfulEvent(Collection<Publication> publications) {
    this.publications = publications;
  }

  public Collection<Publication> getPublications() {
    return this.publications;
  }
}
