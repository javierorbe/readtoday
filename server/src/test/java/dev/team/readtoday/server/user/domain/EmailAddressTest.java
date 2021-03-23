package dev.team.readtoday.server.user.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class EmailAddressTest {

  @ParameterizedTest
  @ValueSource(strings = {"user@somemail.com", "john.smith42@gmail.com"})
  void shouldNotThrowExceptionIfItIsValid(String email) {
    assertDoesNotThrow(() -> new EmailAddress(email));
  }

  @ParameterizedTest
  @ValueSource(strings = {")invalid@mail.d", "another@/service.net"})
  void shouldThrowExceptionIfItIsNotValid(String email) {
    assertThrows(InvalidEmailAddress.class, () -> new EmailAddress(email));
  }
}
