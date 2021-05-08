package dev.team.readtoday.server.customlist.domain;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.domain.User;
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

  public static CustomList randomWithUser(User user) {
    return new CustomList(
        CustomListId.random(),
        new CustomListTitle(faker.bothify("custom ????")),
        user.getId(),
        Collections.emptyList()
    );
  }

  public static CustomList randomWithIdAndUser(CustomListId id, User user) {
    return new CustomList(
        id,
        new CustomListTitle(faker.bothify("custom ????")),
        user.getId(),
        Collections.emptyList()
    );
  }
}
