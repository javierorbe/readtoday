package dev.team.readtoday.client.usecase.readlater;

import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.List;

public class AllPublicationsResponse {
  private List<PublicationResponse> publicationList;

  public List<PublicationResponse> getPublications() {
    return publicationList;
  }
  public void setPublications(
      List<PublicationResponse> publicationList) {
    this.publicationList = publicationList;
  }


}

