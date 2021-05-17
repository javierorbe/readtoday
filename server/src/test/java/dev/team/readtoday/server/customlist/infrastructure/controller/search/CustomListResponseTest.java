package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListResponseTest {

  private final static Faker faker = Faker.instance();

  @Test
  void shouldGetCorrectly() {
    String id = CustomListId.random().toString();
    String title = faker.bothify("CL ????");
    String userId = UserId.random().toString();
    Collection<String> publicationsId = Collections.emptyList();

    CustomListResponse response = new CustomListResponse(id, title, userId, publicationsId);
    assertAll(
        () -> assertEquals(id, response.getId()),
        () -> assertEquals(title, response.getTitle()),
        () -> assertEquals(userId, response.getUserId()),
        () -> assertEquals(publicationsId, response.getPublicationId())
    );
  }
}
