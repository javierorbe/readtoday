package dev.team.readtoday.server.readlater.infrastructure.persistence.controller.get;

import dev.team.readtoday.server.publication.domain.Publication;
import java.util.Collection;

public class ReadLaterListResponse {
  private final Collection<Publication> publicationsList;//tengo que cambiar esto que es una list

  public ReadLaterListResponse(Collection<Publication> publicationsList){
    this.publicationsList = publicationsList;//tengo que cambiar esto que tengo que llamar a la clase PublicationResponse
  }

  public Collection<Publication> getPublicationsList() {
    return publicationsList;
  }
}
