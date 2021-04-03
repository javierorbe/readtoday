package dev.team.readtoday.server.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class UserTest {

  @Test
  void shouldBeEqualIfSameInstance() {
    User user = UserMother.random();
    assertEquals(user, user);
  }

  @Test
  void shouldNotBeEqualToNull() {
    User user = UserMother.random();
    assertNotEquals(null, user);
  }

  @Test
  void shouldBeEqualIfSameId() {
    User expectedUser = UserMother.random();
    User user = UserMother.withId(expectedUser.getId());
    assertEquals(expectedUser, user);
  }
}
