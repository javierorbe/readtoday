package dev.team.readtoday.server.user.domain;

import com.github.javafaker.Faker;

public enum UserMother {
  ;

  private static final Faker faker = Faker.instance();

  public static User random() {
    return new User(
        UserId.random(),
        new Username(faker.bothify("????.????###")),
        EmailAddressMother.random(),
        Role.USER
    );
  }
}
