package dev.team.readtoday.server.category.infrastructure.controller;

import dev.team.readtoday.server.category.domain.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class CategoryResponse {

  private String id;
  private String name;

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

  private static CategoryResponse fromCategory(Category category) {
    return new CategoryResponse(
        category.getId().toString(),
        category.getName().toString()
    );
  }

  public static List<CategoryResponse> fromCategories(Set<Category> categories) {

    List<CategoryResponse> result = new ArrayList<>();

    categories.forEach(category -> result.add(fromCategory(category)));

    return result;
  }
}
