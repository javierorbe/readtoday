package dev.team.readtoday.server.shared.infrastructure.controller.authfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.auth0.jwt.algorithms.Algorithm;
import dev.team.readtoday.server.jwt.domain.InvalidJwtToken;
import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenMother;
import dev.team.readtoday.server.jwt.domain.JwtTokenRepository;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.jwt.infrastructure.Auth0JwtTokenRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class Auth0JwtTokenManagerTest {

  @Test
  void shouldReturnValidTokenForUserId() throws InvalidJwtToken {
    Algorithm algorithm = Algorithm.HMAC256("someSecret");
    JwtTokenRepository manager = new Auth0JwtTokenRepository(algorithm);
    UserId userId = UserId.random();

    JwtToken token = manager.getForUserId(userId);

    assertEquals(userId, manager.validateAndGetUserId(token));
  }

  @Test
  void shouldThrowExceptionIfInvalidToken() {
    Algorithm algorithm = Algorithm.HMAC256("someSecret");
    JwtTokenRepository manager = new Auth0JwtTokenRepository(algorithm);
    JwtToken invalidToken = JwtTokenMother.invalid();

    assertThrows(InvalidJwtToken.class, () -> manager.validateAndGetUserId(invalidToken));
  }
}
