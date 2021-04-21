package dev.team.readtoday.server.publication.application.search;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class CategoryResponse {

  private final String id;
  private final String name;

  private CategoryResponse(
      dev.team.readtoday.server.category.application.search.CategoryResponse category) {
    this.id = category.getId().toString();
    this.name = category.getName().toString();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  static Set<CategoryResponse> fromDomain(
      Collection<dev.team.readtoday.server.category.application.search.CategoryResponse> categories) {
    return categories.stream().map(CategoryResponse::new).collect(Collectors.toUnmodifiableSet());
  }
}
