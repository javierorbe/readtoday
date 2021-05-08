package dev.team.readtoday.server.customlist.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static dev.team.readtoday.server.shared.infrastructure.jooq.tables.CustomList.CUSTOM_LIST;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListMother;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqCustomListRepositoryTest extends BaseJooqIntegrationTest {

  private static CustomListRepository customListRepository;

  private static User user;

  @BeforeAll
  static void setup() {
    start(CUSTOM_LIST, USER);
    customListRepository = getRepository(JooqCustomListRepository.class);
    UserRepository userRepository = getRepository(JooqUserRepository.class);

    // User to use in other test
    user = UserMother.random();
    userRepository.save(user);
  }

  @Test
  void shouldSaveCustomList() {
    CustomList customList = CustomListMother.randomWithUser(user);
    assertDoesNotThrow(() -> customListRepository.save(customList));
  }

  @Test
  void shouldUpdateCustomList() {
    CustomList customList = CustomListMother.randomWithUser(user);
    assertDoesNotThrow(() -> customListRepository.save(customList));

    CustomList customList2 = CustomListMother.randomWithIdAndUser(customList.getId(), user);
    assertDoesNotThrow(() -> customListRepository.save(customList2));
  }

  @AfterAll
  static void afterAll() {
    clearAndShutdown();
  }
}
