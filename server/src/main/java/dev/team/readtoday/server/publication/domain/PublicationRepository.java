package dev.team.readtoday.server.publication.domain;

import java.util.Optional;
import dev.team.readtoday.server.shared.domain.PublicationId;

public interface PublicationRepository {

  void save(Publication publicaiton);

  void remove(Publication publication);

  Optional<Publication> getFromId(PublicationId publicaitionId);
}
