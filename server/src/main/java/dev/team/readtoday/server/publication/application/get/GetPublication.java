package dev.team.readtoday.server.publication.application.get;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.publication.infrastructure.persistance.JooqPublicationRepository;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Optional;

@Service
public class GetPublication {
  private final PublicationRepository repository;
  public GetPublication(PublicationRepository repository){
    this.repository = repository;
  }

  public Optional<Publication> get(PublicationId publicationId){
    return repository.getFromId(publicationId);

  }
}

