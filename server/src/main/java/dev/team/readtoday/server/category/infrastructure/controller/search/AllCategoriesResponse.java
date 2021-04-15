package dev.team.readtoday.server.category.infrastructure.controller.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.channel.infrastructure.controller.search.CategoryResponse;
import java.util.Collection;
import java.util.List;

public class AllCategoriesResponse {

  private final List<CategoryResponse> categories;

  public AllCategoriesResponse(Collection<Category> categories) {
    this.categories = CategoryResponse.fromCategories(categories);
  }

  public List<CategoryResponse> getCategories() {
    return categories;
  }
}
