package dev.team.readtoday.server.category.application;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Optional;

@Service
public final class SearchCategoryByName {

  private final CategoryRepository repository;

  public SearchCategoryByName(CategoryRepository repository) {
    this.repository = repository;
  }

  public Optional<Category> search(CategoryName categoryName) {
    return repository.getByName(categoryName);
  }
}
