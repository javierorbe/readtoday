package dev.team.readtoday.server.channel.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchChannelTest {

  @Test
  void shouldReturnChannelIfFound() {
    ChannelRepository repository = mock(ChannelRepository.class);
    SearchChannel searchChannel = new SearchChannel(repository);
    ChannelId channelId = ChannelId.random();
    when(repository.getFromId(eq(channelId)))
        .thenReturn(Optional.of(ChannelMother.withId(channelId)));

    Channel channel = searchChannel.search(channelId);

    assertEquals(channelId, channel.getId());
  }

  @Test
  void shouldThrowExceptionIfChannelNotFound() {
    ChannelRepository repository = mock(ChannelRepository.class);
    SearchChannel searchChannel = new SearchChannel(repository);
    ChannelId channelId = ChannelId.random();
    when(repository.getFromId(eq(channelId)))
        .thenReturn(Optional.empty());

    assertThrows(ChannelNotFound.class, () -> searchChannel.search(channelId));
  }
}
