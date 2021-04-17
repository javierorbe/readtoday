package dev.team.readtoday.server.publication.infrastructure.rss;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import dev.team.readtoday.server.category.application.CreateCategory;
import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.application.RssFetcher;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.publication.domain.RssFeedException;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.Service;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public final class RomeRssFetcher implements RssFetcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(RomeRssFetcher.class);

  private final SyndFeedInput syndFeedInput;
  private final CreateCategory createCategory;
  private final SearchCategoryByName searchCategoryByName;

  public RomeRssFetcher(SyndFeedInput syndFeedInput,
                        CreateCategory createCategory,
                        SearchCategoryByName searchCategoryByName) {
    this.syndFeedInput = syndFeedInput;
    this.createCategory = createCategory;
    this.searchCategoryByName = searchCategoryByName;
  }

  @Override
  public List<Publication> getPublications(RssUrl rssUrl) throws RssFeedException {
    SyndFeed feed = fetchRssFeed(rssUrl);
    List<SyndEntry> entries = feed.getEntries();
    return buildPublicationList(entries);
  }

  private SyndFeed fetchRssFeed(RssUrl rssUrl) throws RssFeedException {
    try {
      XmlReader reader = new XmlReader(rssUrl.toUrl());
      return syndFeedInput.build(reader);
    } catch (FeedException | IOException e) {
      throw new RssFeedException("Could not read feed", e);
    } catch (IllegalArgumentException e) {
      LOGGER.error("Unknown feed type.", e);
      throw e;
    }
  }

  private List<Publication> buildPublicationList(Collection<? extends SyndEntry> entries) {
    return entries.stream()
        .map(this::buildPublication)
        .collect(ImmutableList.toImmutableList());
  }

  private Publication buildPublication(SyndEntry entry) {
    PublicationId id = new PublicationId(entry.getUri());
    Collection<CategoryId> categories = buildCategories(entry);

    PublicationTitle title =
        (entry.getTitle() == null) ? null : new PublicationTitle(entry.getTitle());

    PublicationDescription description =
        (entry.getDescription() == null)
            ? null
            : new PublicationDescription(entry.getDescription().getValue());

    PublicationDate date =
        (entry.getPublishedDate() == null)
            ? null
            : new PublicationDate(convertToDateTime(entry.getPublishedDate()));

    PublicationLink link =
        (entry.getLink() == null) ? null : new PublicationLink(entry.getLink());

    return new Publication(id, title, description, date, link, categories);
  }

  private Collection<CategoryId> buildCategories(SyndEntry entry) {
    List<SyndCategory> categories = entry.getCategories();
    return categories.stream()
        .map(this::getCategoryId)
        .collect(ImmutableSet.toImmutableSet());
  }

  private CategoryId getCategoryId(SyndCategory syndCategory) {
    CategoryName categoryName = new CategoryName(syndCategory.getName());
    return getOrCreateCategory(categoryName);
  }

  private CategoryId getOrCreateCategory(CategoryName categoryName) {
    Optional<Category> optCategory = searchCategoryByName.search(categoryName);

    if (optCategory.isPresent()) {
      return optCategory.get().getId();
    }

    CategoryId categoryId = CategoryId.random();
    createCategory.create(categoryId, categoryName);
    return categoryId;
  }

  private static OffsetDateTime convertToDateTime(Date dateToConvert) {
    return dateToConvert.toInstant().atOffset(ZoneOffset.UTC);
  }
}
