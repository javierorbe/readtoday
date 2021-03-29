package dev.team.readtoday.server.category.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Optional;

public interface CategoryRepository {

  void save(Category category);

  Optional<Category> getByName(CategoryName categoryName);

  Optional<Category> getById(CategoryId categoryId);
}
