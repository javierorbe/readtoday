package dev.team.readtoday.client.usecase.readlater.get.publications;

import java.util.Collection;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;

public class GetReadLaterPublicationsResponse {

  private Collection<PublicationResponse> publications;

  public Collection<PublicationResponse> getPublications() {
    return this.publications;
  }

  public void setPublications(Collection<PublicationResponse> publications) {
    this.publications = publications;
  }
}
