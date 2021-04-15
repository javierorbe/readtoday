package dev.team.readtoday.server.category.application;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Set;

@Service
public class SearchAllCategories {
  private final CategoryRepository repository;

  public SearchAllCategories(CategoryRepository repository) {
    this.repository = repository;
  }

  public Set<Category> getAll() {
    return repository.getAll();
  }
}
