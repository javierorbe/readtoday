package dev.team.readtoday.server.channel.application.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchChannelTest {

  @Test
  void shouldSearchInRepository() {
    ChannelRepository repository = mock(ChannelRepository.class);
    Collection<ChannelId> channelIds = Stream.generate(ChannelId::random).limit(10L).toList();
    Collection<Channel> expectedChannels = channelIds.stream().map(ChannelMother::withId).toList();
    when(repository.get(eq(channelIds))).thenReturn(expectedChannels);
    SearchChannel search = new SearchChannel(repository);

    Collection<Channel> actualChannels = search.apply(channelIds);

    assertEquals(expectedChannels, actualChannels);
  }
}
