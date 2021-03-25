package dev.team.readtoday.server.user.application;

import com.github.javafaker.Faker;

enum AuthTokenMother {
  ;

  private static final Faker FAKER = Faker.instance();

  static AuthToken random() {
    return new AuthToken(FAKER.random().hex());
  }
}
