package dev.team.readtoday.client.usecase.shared.response;

import dev.team.readtoday.client.model.Category;

public final class CategoryResponse {

  private String id;
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  Category deserialize() {
    return new Category(id, name);
  }
}
