package dev.team.readtoday.server.category.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository {

  void save(Category category);

  Optional<Category> getByName(CategoryName categoryName);

  Collection<Category> getById(Collection<CategoryId> ids);

  Set<Category> getAll();
}
