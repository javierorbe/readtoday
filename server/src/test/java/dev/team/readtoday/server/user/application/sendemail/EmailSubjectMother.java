package dev.team.readtoday.server.user.application.sendemail;

import com.github.javafaker.Faker;

public enum EmailSubjectMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static EmailSubject random() {
    return EmailSubject.fromString(FAKER.lorem().word());
  }
}
