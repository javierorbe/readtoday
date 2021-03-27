package dev.team.readtoday.server.category.domain;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

  void save(Category category);

  Optional<Category> getByName(CategoryName categoryName);
}
