package dev.team.readtoday.server.user.domain;

import com.github.javafaker.Faker;

public final class UsernameMother {

  private static final Faker faker = Faker.instance();

  public static Username random() {
    return new Username(faker.bothify("????.????###"));
  }
}
