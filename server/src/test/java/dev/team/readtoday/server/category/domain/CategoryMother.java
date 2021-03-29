package dev.team.readtoday.server.category.domain;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CategoryId;

public enum CategoryMother {
  ;

  private static final Faker faker = Faker.instance();

  public static Category random() {
    return new Category(
        CategoryId.random(),
        new CategoryName(faker.bothify("category ?????"))
    );
  }
}
