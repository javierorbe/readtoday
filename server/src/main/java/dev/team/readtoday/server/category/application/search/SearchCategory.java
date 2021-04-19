package dev.team.readtoday.server.category.application.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;
import java.util.function.Function;

@Service
public final class SearchCategory implements
    Function<Collection<CategoryId>, Collection<Category>> {

  private final CategoryRepository repository;

  public SearchCategory(CategoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public Collection<Category> apply(Collection<CategoryId> categories) {
    return repository.getById(categories);
  }
}
