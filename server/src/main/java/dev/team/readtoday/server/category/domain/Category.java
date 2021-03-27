package dev.team.readtoday.server.category.domain;

public final class Category {

  private final CategoryName name;

  public Category(CategoryName name) {
    this.name = name;
  }

  public CategoryName getName() {
    return name;
  }
}
