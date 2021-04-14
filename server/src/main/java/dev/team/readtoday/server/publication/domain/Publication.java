package dev.team.readtoday.server.publication.domain;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public final class Publication {

  private final PublicationId id;
  private final PublicationTitle title;
  private final PublicationDescription description;
  private final PublicationDate date;
  private final PublicationLink link;
  private final ImmutableCollection<CategoryId> categories;

  /**
   * Constructs a publication.
   *
   * <p>All elements are optional, however ID should not be null and at least one of title or
   * description must be present.
   */
  public Publication(PublicationId id,
                     PublicationTitle title,
                     PublicationDescription description,
                     PublicationDate date,
                     PublicationLink link,
                     Collection<CategoryId> categories) {
    this.id = Objects.requireNonNull(id);
    this.title = title;
    this.description = description;
    this.date = date;
    this.link = link;
    this.categories = ImmutableSet.copyOf(categories);
    assertTitleOrDescriptionIsPresent();
  }

  private void assertTitleOrDescriptionIsPresent() {
    if ((title == null) && (description == null)) {
      throw new InvalidPublication("At least one of title or description must be present.");
    }
  }

  public PublicationId getId() {
    return id;
  }

  public Optional<PublicationTitle> getTitle() {
    return Optional.ofNullable(title);
  }

  public Optional<PublicationDescription> getDescription() {
    return Optional.ofNullable(description);
  }

  public Optional<PublicationDate> getDate() {
    return Optional.ofNullable(date);
  }

  public Optional<PublicationLink> getLink() {
    return Optional.ofNullable(link);
  }

  public ImmutableCollection<CategoryId> getCategories() {
    return categories;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    Publication pub = (Publication) o;
    return id.equals(pub.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
