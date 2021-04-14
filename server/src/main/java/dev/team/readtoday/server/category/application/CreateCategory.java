package dev.team.readtoday.server.category.application;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.Service;

@Service
public final class CreateCategory {

  private final CategoryRepository repository;

  public CreateCategory(CategoryRepository repository) {
    this.repository = repository;
  }

  public void create(CategoryName name) {
    CategoryId id = CategoryId.random();
    Category category = new Category(id, name);
    repository.save(category);
  }
}
