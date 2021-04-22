package dev.team.readtoday.server.publication.application.findforchannel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class FindRelevantPublicationsTest {

  @Test
  void shouldThrowExceptionIfChannelDoesNotExists() {

    PublicationRepository repository = mock(PublicationRepository.class);
    ChannelId channelId = ChannelId.random();
    List<ChannelId> channelIds = new ArrayList<>();
    channelIds.add(channelId);
    when(repository.getRelevant(channelId)).thenReturn(Optional.empty());

    FindRelevantPublications finder = new FindRelevantPublications(repository);
    assertThrows(ChannelNotFound.class, () -> finder.apply(channelIds));
  }

  @Test
  void shouldNotThrowExceptionIfChannelDoesExists() {
    PublicationRepository repository = mock(PublicationRepository.class);
    ChannelId channelId = ChannelId.random();
    List<ChannelId> channelIds = new ArrayList<>();
    channelIds.add(channelId);
    Publication publication = mock(Publication.class);
    when(repository.getRelevant(channelId)).thenReturn(Optional.of(publication));

    FindRelevantPublications finder = new FindRelevantPublications(repository);
    assertDoesNotThrow(() -> finder.apply(channelIds));
  }
}
