package dev.team.readtoday.server.category.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.category.infrastructure.controller.create.CategoryCreationRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryCreationRequestTest {

  private static final Faker faker = Faker.instance();

  @Test
  void shouldSetAndGetCorrectly() {
    CategoryCreationRequest request = new CategoryCreationRequest();
    String name = faker.bothify("category name ????");
    request.setName(name);

    assertEquals(name, request.getName());
  }
}
