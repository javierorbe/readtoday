package dev.team.readtoday.client.model;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class Publication implements Comparable<Publication>{

  private final String id;
  private final String title;
  private final String description;
  private final OffsetDateTime date;
  private final String link;
  private final Set<Category> categories;

  public Publication(String id,
                     String title,
                     String description,
                     OffsetDateTime date,
                     String link,
                     Set<Category> categories) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.date = date;
    this.link = link;
    this.categories = categories;
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

  public Set<Category> getCategories() {
    return Collections.unmodifiableSet(categories);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Publication that = (Publication) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public int compareTo(Publication o) {
    return title.compareToIgnoreCase(o.title);
  }
}
