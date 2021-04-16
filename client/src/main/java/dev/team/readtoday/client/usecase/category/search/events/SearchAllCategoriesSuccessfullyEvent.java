package dev.team.readtoday.client.usecase.category.search.events;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.model.Category;

public class SearchAllCategoriesSuccessfullyEvent {

  private final ImmutableCollection<Category> categories;

  public SearchAllCategoriesSuccessfullyEvent(
      ImmutableCollection<Category> categories) {
    this.categories = categories;
  }

  public ImmutableCollection<Category> getCategories() {
    return categories;
  }
}
