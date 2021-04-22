package dev.team.readtoday.server.publication.domain;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import java.util.Optional;

public interface PublicationRepository {

  void save(Publication publicaiton);

  void remove(Publication publication);

  Optional<Publication> getFromId(PublicationId publicaitionId);

  Optional<Publication> getRelevant(ChannelId channelId);
}
