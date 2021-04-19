package dev.team.readtoday.server.category.application.search;

import dev.team.readtoday.server.shared.domain.bus.query.Query;
import java.util.Collection;
import java.util.Collections;

public final class SearchCategoryQuery implements Query<SearchCategoryResponse> {

  private final Collection<String> categoryIds;

  public SearchCategoryQuery(Collection<String> categoryIds) {
    this.categoryIds = Collections.unmodifiableCollection(categoryIds);
  }

  Collection<String> getCategoryIds() {
    return categoryIds;
  }
}
