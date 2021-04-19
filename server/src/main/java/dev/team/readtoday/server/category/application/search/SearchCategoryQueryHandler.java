package dev.team.readtoday.server.category.application.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.util.Collection;

@Service
public final class SearchCategoryQueryHandler
    implements QueryHandler<SearchCategoryQuery, SearchCategoryResponse> {

  private final SearchCategory searchCategory;

  public SearchCategoryQueryHandler(SearchCategory searchCategory) {
    this.searchCategory = searchCategory;
  }

  @Override
  public SearchCategoryResponse handle(SearchCategoryQuery query) {
    Collection<CategoryId> ids = toDomain(query.getCategoryIds());
    Collection<Category> categories = searchCategory.apply(ids);
    return SearchCategoryResponse.fromDomain(categories);
  }

  private static Collection<CategoryId> toDomain(Collection<String> categories) {
    return categories.stream().map(CategoryId::fromString).toList();
  }
}
