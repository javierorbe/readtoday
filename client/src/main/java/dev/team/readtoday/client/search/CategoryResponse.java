package dev.team.readtoday.client.search;

import dev.team.readtoday.client.model.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class CategoryResponse {

  private String id;
  private String name;

  public CategoryResponse() {

  }

  public CategoryResponse(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  private Category toCategory() {
    return new Category(
        UUID.fromString(id),
        name
    );
  }

  public static Map<UUID, Category> toCategories(List<CategoryResponse> categoriesResponse) {
    Map<UUID, Category> categories = new HashMap<>();

    categoriesResponse.forEach(categoryResponse -> categories.put(
        UUID.fromString(categoryResponse.id),
        categoryResponse.toCategory()));

    return categories;
  }
}
