package dev.team.readtoday.server.category.application;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;

@Service
public final class SearchCategoriesById {

  private final CategoryRepository repository;

  public SearchCategoriesById(CategoryRepository repository) {
    this.repository = repository;
  }

  public Collection<Category> search(Collection<CategoryId> categories) {
    return repository.getById(categories);
  }
}
