package dev.team.readtoday.client.usecase.subscription.publications;

import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.Collection;

public class PublicationRequestSuccesfulEvent {

  private final Collection<PublicationResponse> publications;

  public PublicationRequestSuccesfulEvent(Collection<PublicationResponse> publications) {
    this.publications = publications;
  }

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }
}
