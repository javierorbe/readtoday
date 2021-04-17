package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.shared.domain.StringValueObject;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class PublicationResponse {

  private final String id;
  private final String title;
  private final String description;
  private final LocalDateTime date;
  private final String link;
  private final Set<CategoryResponse> categories;

  PublicationResponse(Publication publication, Collection<Category> categories) {
    id = publication.getId().toString();
    title = publication.getTitle().map(StringValueObject::toString).orElse(null);
    date = publication.getDate().map(PublicationDate::getDateTime).orElse(null);
    description = publication.getDescription().map(StringValueObject::toString).orElse(null);
    link = publication.getLink().map(StringValueObject::toString).orElse(null);
    this.categories = CategoryResponse.setOf(categories);
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public String getLink() {
    return link;
  }

  public Set<CategoryResponse> getCategories() {
    return categories;
  }

  static List<PublicationResponse> listOf(List<Publication> publications,
                                          Collection<Category> categories) {
    return publications.stream().map(pub -> new PublicationResponse(pub, categories)).toList();
  }
}
