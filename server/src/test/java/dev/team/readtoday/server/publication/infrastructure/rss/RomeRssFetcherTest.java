package dev.team.readtoday.server.publication.infrastructure.rss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.category.application.CreateCategory;
import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.application.RssFetcher;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.RssFeedException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class RomeRssFetcherTest {

  @Test
  void shouldRetrievePublications() throws Exception {
    // Given
    CategoryRepository categoryRepository = mock(CategoryRepository.class);
    CreateCategory createCategory = new CreateCategory(categoryRepository);
    SearchCategoryByName searchCategory = new SearchCategoryByName(categoryRepository);
    RssFetcher rssFetcher = new RomeRssFetcher(createCategory, searchCategory);
    CategoryName eventsName = new CategoryName("Events");
    when(categoryRepository.getByName(eq(eventsName)))
        .thenReturn(Optional.of(CategoryMother.withName(eventsName)));
    when(categoryRepository.getByName(not(eq(eventsName)))).thenReturn(Optional.empty());

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
      System.out.println(expectedPublication.getDate().toString());
      System.out.println(actualPublication.getDate().toString());
      assertEquals(expectedPublication.getDate(), actualPublication.getDate());
    }
  }

  @Test
  void shouldThrowExceptionIfInvalidRssUrl() {
    // Given
    CategoryRepository categoryRepository = mock(CategoryRepository.class);
    CreateCategory createCategory = new CreateCategory(categoryRepository);
    SearchCategoryByName searchCategory = new SearchCategoryByName(categoryRepository);
    RssFetcher rssFetcher = new RomeRssFetcher(createCategory, searchCategory);

    // Then
    assertThrows(RssFeedException.class, () -> rssFetcher.getPublications(getInvalidTestRssUrl()));
  }

  @Test
  void shouldThrowExceptionIfInvalidFeedUrl() {
    // Given
    CategoryRepository categoryRepository = mock(CategoryRepository.class);
    CreateCategory createCategory = new CreateCategory(categoryRepository);
    SearchCategoryByName searchCategory = new SearchCategoryByName(categoryRepository);
    RssFetcher rssFetcher = new RomeRssFetcher(createCategory, searchCategory);

    // Then
    assertThrows(IllegalArgumentException.class, () -> rssFetcher.getPublications(getInvalidTestFeedUrl()));
  }

  private static RssUrl getTestRssUrl() {
    URL url = RomeRssFetcherTest.class.getResource(
        "/dev/team/readtoday/server/publication/infrastructure/rss/example_rss_feed.xml");
    return new RssUrl(url.toString());
  }

  private static RssUrl getInvalidTestRssUrl() {
    URL url = RomeRssFetcherTest.class.getResource(
        "/dev/team/readtoday/server/publication/infrastructure/rss/invalid_rss_feed.xml");
    return new RssUrl(url.toString());
  }

  private static RssUrl getInvalidTestFeedUrl() {
    URL url = RomeRssFetcherTest.class.getResource(
        "/dev/team/readtoday/server/publication/infrastructure/rss/invalid_feed.xml");
    return new RssUrl(url.toString());
  }
}
