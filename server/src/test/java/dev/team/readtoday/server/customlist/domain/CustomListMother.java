package dev.team.readtoday.server.customlist.domain;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Arrays;
import java.util.Collections;

public enum CustomListMother {
  ;

  private static final Faker faker = Faker.instance();

  public static CustomList random() {
    return new CustomList(
        CustomListId.random(),
        new CustomListTitle(faker.bothify("custom ????")),
        UserId.random(),
        Collections.emptyList()
    );
  }

  public static CustomList randomWithUserId(UserId userId) {
    return new CustomList(
        CustomListId.random(),
        new CustomListTitle(faker.bothify("custom ????")),
        userId,
        Collections.emptyList()
    );
  }

  public static CustomList randomWithIdAndUser(CustomListId id, UserId userId) {
    return new CustomList(
        id,
        new CustomListTitle(faker.bothify("custom ????")),
        userId,
        Collections.emptyList()
    );
  }

  public static CustomList withRandomPublications() {
    return new CustomList(
        CustomListId.random(),
        new CustomListTitle(faker.bothify("custom ????")),
        UserId.random(),
        Arrays.asList(PublicationId.random(), PublicationId.random())
    );
  }

  public static CustomList withUserIdAndRandomPublications(UserId userId) {
    return new CustomList(
        CustomListId.random(),
        new CustomListTitle(faker.bothify("custom ????")),
        userId,
        Arrays.asList(PublicationId.random(), PublicationId.random())
    );
  }
}
