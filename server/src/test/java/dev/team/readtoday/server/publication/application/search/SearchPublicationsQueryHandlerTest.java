package dev.team.readtoday.server.publication.application.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.category.application.search.SearchCategoryQuery;
import dev.team.readtoday.server.category.application.search.SearchCategoryResponse;
import dev.team.readtoday.server.channel.application.search.SearchChannelQuery;
import dev.team.readtoday.server.channel.application.search.SearchChannelQueryResponse;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchPublicationsQueryHandlerTest {

  @Test
  void shouldSearchPublications() {
    // Given
    var queryBus = mock(QueryBus.class);
    var searchPublications = mock(SearchPublications.class);

    var channelIds =
        Stream.generate(() -> ChannelId.random().toString()).limit(5).toList();
    var channels =
        channelIds.stream().map(id -> ChannelMother.withId(ChannelId.fromString(id))).toList();

    when(queryBus.ask(any(SearchChannelQuery.class)))
        .thenReturn(SearchChannelQueryResponse.fromDomainObject(channels));
    when(queryBus.ask(any(SearchCategoryQuery.class)))
        .thenReturn(SearchCategoryResponse.fromDomain(List.of()));
    var rssUrls = channels.stream().map(Channel::getRssUrl).toList();
    var publications = Stream.generate(PublicationMother::random).limit(10).toList();
    when(searchPublications.apply(rssUrls)).thenReturn(publications);

    var query = new SearchPublicationsQuery(channelIds);
    var handler = new SearchPublicationsQueryHandler(queryBus, searchPublications);

    // When
    var response = handler.handle(query);

    // Then
    assertEquals(publications.size(), response.getPublications().size());
  }
}
