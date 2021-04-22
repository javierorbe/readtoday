package dev.team.readtoday.server.publication.application.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.infrastructure.controller.search.ChannelResponse;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.shared.domain.StringValueObject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PublicationResponse {

  private final String id;
  private final String title;
  private final String description;
  private final OffsetDateTime date;
  private final String link;
  private final Set<CategoryResponse> categories;

  PublicationResponse(Publication publication,
                      Collection<dev.team.readtoday.server.category.application.search.CategoryResponse> categories) {
    id = publication.getId().toString();
    title = publication.getTitle().map(StringValueObject::toString).orElse(null);
    date = publication.getDate().map(PublicationDate::getDateTime).orElse(null);
    description = publication.getDescription().map(StringValueObject::toString).orElse(null);
    link = publication.getLink().map(StringValueObject::toString).orElse(null);
    this.categories = CategoryResponse.fromDomain(categories);
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

  public OffsetDateTime getDate() {
    return date;
  }

  public String getLink() {
    return link;
  }

  public Set<CategoryResponse> getCategories() {
    return categories;
  }

  public static List<PublicationResponse> fromDomain(List<Publication> publications,
                                              Collection<dev.team.readtoday.server.category.application.search.CategoryResponse> categories) {
    return publications.stream().map(pub -> new PublicationResponse(pub, categories)).toList();
  }
}
