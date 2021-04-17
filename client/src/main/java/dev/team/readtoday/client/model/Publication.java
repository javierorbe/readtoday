package dev.team.readtoday.client.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public final class Publication {

  private final String id;
  private final String title;
  private final String description;
  private final LocalDateTime date;
  private final String link;
  private final Set<Category> categories;

  public Publication(String id,
                     String title,
                     String description,
                     LocalDateTime date,
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

  public LocalDateTime getDate() {
    return date;
  }

  public String getLink() {
    return link;
  }

  public Set<Category> getCategories() {
    return Collections.unmodifiableSet(categories);
  }
}
