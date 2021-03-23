package dev.team.readtoday.server.user.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class UsernameTest {

  @ParameterizedTest
  @ValueSource(strings = {"mickey", "john.smith"})
  void shouldNotThrowExceptionIfItIsValid(String username) {
    assertDoesNotThrow(() -> new Username(username));
  }

  @ParameterizedTest
  @ValueSource(strings = {"be", "very_long_username_that_is_invalid"})
  void shouldThrowExceptionIfItIsNotValid(String username) {
    assertThrows(IllegalArgumentException.class, () -> new Username(username));
  }
}
