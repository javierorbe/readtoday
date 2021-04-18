package dev.team.readtoday.client.usecase.shared.response;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Publication;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PublicationResponse {

  private String id;
  private String title;
  private String description;
  private OffsetDateTime date;
  private String link;
  private Set<CategoryResponse> categories;

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public void setCategories(Set<CategoryResponse> categories) {
    this.categories = categories;
  }

  private Publication deserialize() {
    return new Publication(
        id,
        title,
        description,
        date,
        link,
        getDeserializedCategories()
    );
  }

  private Set<Category> getDeserializedCategories() {
    return categories.stream()
        .map(CategoryResponse::deserialize)
        .collect(Collectors.toUnmodifiableSet());
  }

  public static ImmutableList<Publication> deserializeList(List<PublicationResponse> list) {
    return list.stream()
        .map(PublicationResponse::deserialize)
        .collect(ImmutableList.toImmutableList());
  }
}
