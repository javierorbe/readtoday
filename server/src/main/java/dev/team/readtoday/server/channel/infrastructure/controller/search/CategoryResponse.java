package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.domain.Category;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class CategoryResponse {

  private final String id;
  private final String name;

  private CategoryResponse(Category category) {
    id = category.getId().toString();
    name = category.getName().toString();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static List<CategoryResponse> fromCategories(Collection<Category> categories) {
    return categories.stream()
        .map(CategoryResponse::new)
        .collect(Collectors.toList());
  }
}
