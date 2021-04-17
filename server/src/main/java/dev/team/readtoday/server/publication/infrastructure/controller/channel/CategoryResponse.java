package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import dev.team.readtoday.server.category.domain.Category;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class CategoryResponse {

  private final String id;
  private final String name;

  private CategoryResponse(Category category) {
    this.id = category.getId().toString();
    this.name = category.getName().toString();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  static Set<CategoryResponse> setOf(Collection<Category> categories) {
    return categories.stream()
        .map(CategoryResponse::new)
        .collect(Collectors.toUnmodifiableSet());
  }
}
