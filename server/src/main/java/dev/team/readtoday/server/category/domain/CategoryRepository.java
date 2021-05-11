package dev.team.readtoday.server.category.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository {

  /**
   * Create or update a category.
   *
   * @param category to create or update.
   */
  void save(Category category);

  /**
   * Returns an Optional of categories given the name of the category.
   *
   * @param categoryName name of the category.
   * @return Optional of Category
   */
  Optional<Category> getByName(CategoryName categoryName);

  /**
   * Returns a Collection of categories given its ids.
   *
   * @param ids of categories to get.
   * @return a Collection of categories
   */
  Collection<Category> getById(Collection<CategoryId> ids);

  /**
   * Returns all categories.
   *
   * @return Set of categories
   */
  Set<Category> getAll();
}
