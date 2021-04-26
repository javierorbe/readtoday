package dev.team.readtoday.server.readlater.infrastructure.persistence.controller.get;

import dev.team.readtoday.server.publication.domain.Publication;
import java.util.Collection;
import java.util.List;

public class ReadLaterListResponse {
  private final List<PublicationResponse> publicationsList;

  public ReadLaterListResponse(Collection<Publication> publicationsList){
    this.publicationsList = PublicationResponse.fromPublication(publicationsList);
  }

  public List<PublicationResponse> getPublicationsList() {
    return publicationsList;
  }
}

