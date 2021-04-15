package dev.team.readtoday.client.usecase.category.search.messages;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.usecase.channel.search.CategoryResponse;
import java.util.List;

public class AllCategoryResponse {

  private List<CategoryResponse> categories;

  public void setCategories(
      List<CategoryResponse> categories) {
    this.categories = categories;
  }

  public ImmutableCollection<Category> toCategoriesCollection() {
    return categories.stream()
        .map(res -> new Category(res.getId(), res.getName()))
        .collect(ImmutableSet.toImmutableSet());
  }
}
