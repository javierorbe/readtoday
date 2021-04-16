package dev.team.readtoday.server.category.domain;

import com.github.javafaker.Faker;

public enum CategoryNameMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static CategoryName random() {
    return new CategoryName(FAKER.lorem().word());
  }
}
