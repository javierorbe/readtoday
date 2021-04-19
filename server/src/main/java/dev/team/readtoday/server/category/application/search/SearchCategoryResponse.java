package dev.team.readtoday.server.category.application.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import java.util.Collection;
import java.util.Collections;

public final class SearchCategoryResponse implements QueryResponse {

  private final Collection<CategoryResponse> categories;

  private SearchCategoryResponse(Collection<CategoryResponse> categories) {
    this.categories = Collections.unmodifiableCollection(categories);
  }

  public Collection<CategoryResponse> getCategories() {
    return categories;
  }

  static SearchCategoryResponse fromDomain(Collection<Category> categories) {
    var serializedCategories = categories.stream().map(CategoryResponse::fromDomain).toList();
    return new SearchCategoryResponse(serializedCategories);
  }
}
