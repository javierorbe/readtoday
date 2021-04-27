package dev.team.readtoday.server.channel.application.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchChannelQueryHandlerTest {

  private static final Faker FAKER = Faker.instance();

  @Test
  void shouldReturnCorrectQueryResponse() {
    // Given
    int channelAmount = FAKER.number().numberBetween(5, 15);
    var rawChannelIds =
        Stream.generate(() -> ChannelId.random().toString()).limit(channelAmount).toList();
    var channelIds = rawChannelIds.stream().map(ChannelId::fromString).toList();
    Collection<Channel> channelResult = channelIds.stream().map(ChannelMother::withId).toList();

    SearchChannel searchChannel = mock(SearchChannel.class);
    when(searchChannel.apply(channelIds)).thenReturn(channelResult);
    SearchChannelQueryHandler handler = new SearchChannelQueryHandler(searchChannel);

    SearchChannelQuery query = new SearchChannelQuery(rawChannelIds);

    // When
    SearchChannelQueryResponse response = handler.handle(query);

    // Then
    Collection<ChannelResponse> actualChannels = response.getChannels();
    assertEquals(channelAmount, actualChannels.size());
  }
}
