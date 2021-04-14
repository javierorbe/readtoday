package dev.team.readtoday.client.usecase.category.create.events;

import dev.team.readtoday.client.usecase.category.create.messages.CategoryCreationRequest;

public final class CategoryCreationEvent {

  private final CategoryCreationRequest request;

  public CategoryCreationEvent(CategoryCreationRequest request) {
    this.request = request;
  }

  public CategoryCreationRequest getRequest() {
    return request;
  }

}
