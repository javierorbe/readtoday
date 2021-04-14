package dev.team.readtoday.server.publication.domain;

import com.github.javafaker.Faker;

public enum PublicationTitleMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static PublicationTitle random() {
    return new PublicationTitle(FAKER.bothify("???? ?? ????? ???"));
  }
}
