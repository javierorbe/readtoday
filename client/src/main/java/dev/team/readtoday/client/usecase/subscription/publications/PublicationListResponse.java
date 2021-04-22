package dev.team.readtoday.client.usecase.subscription.publications;

import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.Collection;

public class PublicationListResponse {

  private Collection<PublicationResponse> publications;

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }

  public void setPublications(Collection<PublicationResponse> publications) {
    this.publications = publications;
  }
}
