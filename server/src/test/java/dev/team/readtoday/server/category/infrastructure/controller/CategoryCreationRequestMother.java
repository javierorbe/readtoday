package dev.team.readtoday.server.category.infrastructure.controller;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.category.infrastructure.controller.create.CategoryCreationRequest;

public enum CategoryCreationRequestMother {
  ;

  private static final Faker faker = Faker.instance();

  public static CategoryCreationRequest random() {
    CategoryCreationRequest request = new CategoryCreationRequest();
    request.setName(faker.bothify("Category name ????"));
    return request;
  }
}
