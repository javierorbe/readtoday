package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.Collections;

public enum CustomListResponseMother {

  ;

  private final static Faker faker = Faker.instance();

  public static CustomListResponse random() {
    String id = CustomListId.random().toString();
    String title = faker.bothify("CL ????");
    String userId = UserId.random().toString();
    Collection<String> publicationsId = Collections.emptyList();

    return new CustomListResponse(id, title, userId, publicationsId);
  }

}
