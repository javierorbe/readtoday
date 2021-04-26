package dev.team.readtoday.client.usecase.readlater;

import dev.team.readtoday.client.model.Category;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;

public class ReadLaterRequest {
  private final String id;
  private final String title;
  private final String description;
  private final OffsetDateTime date;
  private final String link;
  private final Collection<String> categories;

  public ReadLaterRequest(String id, String title,
      String description,
      OffsetDateTime date, String link,
      Collection<String> categories) {
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

  public Collection<String> getCategories() {
    return categories;
  }
}
