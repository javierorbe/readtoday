package dev.team.readtoday.server.readlater.infrastructure.persistence.controller.get;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.time.OffsetDateTime;
import java.util.Collection;

public class PublicationRequest {
  private  String title;
  private  String id;
  private  String description;
  private  OffsetDateTime date;
  private  String link;
  private  Collection<String> categories;



  public String getTitle() {
    return title;
  }

  public String getId() {
    return id;
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

  public void setTitle(String title) {
    this.title = title;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setCategories(Collection<String> categories) {
    this.categories = categories;
  }
}

