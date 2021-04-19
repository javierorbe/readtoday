package dev.team.readtoday.server.category.application.search;

import dev.team.readtoday.server.category.domain.Category;

public final class CategoryResponse {

  private final String id;
  private final String name;

  private CategoryResponse(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  static CategoryResponse fromDomain(Category category) {
    return new CategoryResponse(category.getId().toString(), category.getName().toString());
  }
}
