package dev.team.readtoday.client.usecase.category.create.messages;

public class CategoryCreationRequest {

  private final String name;

  public CategoryCreationRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
