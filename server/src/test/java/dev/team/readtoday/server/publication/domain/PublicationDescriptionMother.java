package dev.team.readtoday.server.publication.domain;

import com.github.javafaker.Faker;

public enum PublicationDescriptionMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static PublicationDescription random() {
    return new PublicationDescription(FAKER.lorem().sentence());
  }
}
