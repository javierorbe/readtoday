package dev.team.readtoday.server.user.application.sendemail;

import com.github.javafaker.Faker;

public enum EmailContentMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static EmailContent random() {
    int wordCount = FAKER.random().nextInt(100);
    return EmailContent.fromString(String.join(" ", FAKER.lorem().words(wordCount)));
  }
}
