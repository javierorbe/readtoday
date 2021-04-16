package dev.team.readtoday.server.publication.infrastructure.rss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import dev.team.readtoday.server.category.application.CreateCategory;
import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.application.RssFetcher;
import dev.team.readtoday.server.publication.domain.Publication;
import java.io.Reader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class RomeRssFetcherTest {

  @Test
  void shouldRetrievePublications() throws Exception {
    // Given
    CategoryRepository categoryRepository = mock(CategoryRepository.class);
    CreateCategory createCategory = new CreateCategory(categoryRepository);
    SearchCategoryByName searchCategory = new SearchCategoryByName(categoryRepository);
    SyndFeedInput syndFeedInput = mock(SyndFeedInput.class);
    RssFetcher rssFetcher =
        new RomeRssFetcher(syndFeedInput, createCategory, searchCategory);
    when(categoryRepository.getByName(any())).thenReturn(Optional.empty());
    SyndFeed syndFeed = mock(SyndFeed.class);
    List<SyndEntryFake> syndEntryFakes = TestSyndEntryList.buildEntryList();
    when(syndFeed.getEntries()).thenReturn(Collections.unmodifiableList(syndEntryFakes));
    when(syndFeedInput.build((Reader) any())).thenReturn(syndFeed);

    // When
    List<Publication> actualPublications = rssFetcher.getPublications(getTestRssUrl());

    // Then
    List<Publication> expectedPublications = TestSyndEntryList.buildPublicationList();

    assertEquals(expectedPublications.size(), actualPublications.size());
    for (int i = 0; i < expectedPublications.size(); i++) {
      Publication expectedPublication = expectedPublications.get(i);
      Publication actualPublication = actualPublications.get(i);

      assertEquals(expectedPublication.getTitle(), actualPublication.getTitle());
      assertEquals(expectedPublication.getDescription(), actualPublication.getDescription());
      assertEquals(expectedPublication.getLink(), actualPublication.getLink());
      assertEquals(expectedPublication.getDate(), actualPublication.getDate());
    }
  }

  private static RssUrl getTestRssUrl() {
    URL url = RomeRssFetcherTest.class.getResource(
        "/dev/team/readtoday/server/publication/infrastructure/rss/example_rss_feed.xml");
    return new RssUrl(url.toString());
  }
}
