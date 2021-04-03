package dev.team.readtoday.server.category.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Objects;

public final class Category {

  private final CategoryId id;
  private final CategoryName name;

  public Category(CategoryId id, CategoryName name) {
    this.id = id;
    this.name = name;
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
    if (!(o instanceof Category)) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }
}
