package dev.team.readtoday.server.publication.application.findforchannel;

import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class FindRelevantPublications implements Function<List<ChannelId>, List<Publication>> {

  private final PublicationRepository publicationRepository;

  public FindRelevantPublications(PublicationRepository publicationRepository) {
    this.publicationRepository = publicationRepository;
  }

  @Override
  public List<Publication> apply(List<ChannelId> channelIds) {

    List<Publication> result = new ArrayList<>();

    for (ChannelId id : channelIds) {
      Optional<Publication> optPublication = publicationRepository.getRelevant(id);

      optPublication.ifPresentOrElse(result::add, () -> {
        throw new ChannelNotFound();
      });
    }
    return result;
  }
}
