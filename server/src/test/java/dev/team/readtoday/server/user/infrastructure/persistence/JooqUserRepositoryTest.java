package dev.team.readtoday.server.user.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.EmailAddressMother;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqUserRepositoryTest {

  private static JooqConnectionBuilder jooq;
  private static UserRepository repository;

  @BeforeAll
  static void setUp() {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    repository = new JooqUserRepository(jooq.getContext());

    DSLContext ctx = jooq.getContext();
    ctx.deleteFrom(USER).execute();
  }

  @AfterAll
  static void cleanUp() {
    jooq.close();
  }

  @Test
  void shouldSaveAUser() {
    User user = UserMother.random();

    assertDoesNotThrow(() -> repository.save(user));
  }

  @Test
  void shouldUpdateAnExistingUser() {
    User originalUser = UserMother.random();
    repository.save(originalUser);

    Optional<User> optUser = repository.getByEmailAddress(originalUser.getEmailAddress());
    User user = optUser.orElseThrow();
    user.setRole(Role.ADMIN);

    assertDoesNotThrow(() -> repository.save(user));
  }

  @Test
  void shouldReturnAnExistingUser() {
    User originalUser = UserMother.random();
    repository.save(originalUser);

    Optional<User> optUser = repository.getByEmailAddress(originalUser.getEmailAddress());
    User user = optUser.orElseThrow();

    assertEquals(originalUser.getId(), user.getId());
    assertEquals(originalUser.getUsername(), user.getUsername());
    assertEquals(originalUser.getEmailAddress(), user.getEmailAddress());
    assertEquals(originalUser.getRole(), user.getRole());
  }

  @Test
  void shouldNotReturnANonExistingUser() {
    EmailAddress email = EmailAddressMother.random();

    Optional<User> optUser = repository.getByEmailAddress(email);

    assertTrue(optUser.isEmpty());
  }
}