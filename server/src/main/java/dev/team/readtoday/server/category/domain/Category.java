package dev.team.readtoday.server.category.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Objects;

/**
 * Represents a category, ie. Sports, Entertainment, Football, Cars, etc.
 * A \ref Channel "Channel" belongs to zero or more categories.
 * A \ref Publication "Publication" belongs to zero or more categories.
 */
public final class Category {

  /**
   * Represents the id (UUID) of a category.
   */
  private final CategoryId id;
  private final CategoryName name;

  public Category(CategoryId id, CategoryName name) {
    this.id = Objects.requireNonNull(id);
    this.name = Objects.requireNonNull(name);
  }

  public CategoryId getId() {
    return id;
  }

  public CategoryName getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    Category category = (Category) o;
    return id.equals(category.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
