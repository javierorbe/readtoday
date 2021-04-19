package dev.team.readtoday.server.user.application.profile;

import com.github.javafaker.Faker;

public enum AccessTokenMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static AccessToken random() {
    return new AccessToken(FAKER.random().hex());
  }
}
