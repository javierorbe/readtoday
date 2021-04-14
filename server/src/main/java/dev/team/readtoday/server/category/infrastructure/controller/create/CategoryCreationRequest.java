package dev.team.readtoday.server.category.infrastructure.controller.create;

public class CategoryCreationRequest {
  private String name;


  public CategoryCreationRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
