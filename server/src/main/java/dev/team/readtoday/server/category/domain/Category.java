package dev.team.readtoday.server.category.domain;

public class Category {

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
}