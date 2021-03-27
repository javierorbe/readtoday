package dev.team.readtoday.server.category.domain;

import com.github.javafaker.Faker;

public enum CategoryMother {
  ;

  private static final Faker faker = Faker.instance();

  public static Category random() {
    return new Category(
        new CategoryName(faker.bothify("category ?????"))
    );
  }
}
