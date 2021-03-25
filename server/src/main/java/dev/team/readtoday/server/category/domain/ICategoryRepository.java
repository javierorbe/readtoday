package dev.team.readtoday.server.category.domain;

import java.util.Optional;
import java.util.UUID;

public interface ICategoryRepository {

  void save(Category category);

  Optional<Category> getById(CategoryId id);

}
