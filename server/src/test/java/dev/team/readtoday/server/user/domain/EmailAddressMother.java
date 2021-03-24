package dev.team.readtoday.server.user.domain;

import com.github.javafaker.Faker;

public enum EmailAddressMother {
  ;

  private static final Faker faker = Faker.instance();

  public static EmailAddress random() {
    return new EmailAddress(faker.bothify("????##@gmail.com"));
  }
}
