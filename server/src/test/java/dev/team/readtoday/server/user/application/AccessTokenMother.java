package dev.team.readtoday.server.user.application;

import com.github.javafaker.Faker;

enum AccessTokenMother {
  ;

  private static final Faker FAKER = Faker.instance();

  static AccessToken random() {
    return new AccessToken(FAKER.random().hex());
  }
}
