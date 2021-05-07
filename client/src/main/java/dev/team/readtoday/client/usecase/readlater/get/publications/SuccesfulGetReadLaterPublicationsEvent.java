package dev.team.readtoday.client.usecase.readlater.get.publications;

import java.util.Collection;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;

public class SuccesfulGetReadLaterPublicationsEvent {

  private final Collection<PublicationResponse> publications;

  public SuccesfulGetReadLaterPublicationsEvent(Collection<PublicationResponse> publicaitons) {
    this.publications = publicaitons;
  }

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }
}
