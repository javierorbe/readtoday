package dev.team.readtoday.server.customlist.application;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.UserId;

public enum CreateCustomListCommandMother {

  ;

  private final static Faker faker = Faker.instance();

  public static CreateCustomListCommand random() {
    return new CreateCustomListCommand(
        faker.bothify("custom list ????"),
        UserId.random().toString()
    );
  }
}
