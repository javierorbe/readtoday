package dev.team.readtoday.client.model;

import java.util.UUID;

public final class Category implements Comparable<Category> {

  private final String id;
  private final String name;

  public Category(UUID id, String name) {
    this.id = id.toString();
    this.name = name;
  }

  public Category(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
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

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(Category other) {
    return name.compareToIgnoreCase(other.name);
  }
}
