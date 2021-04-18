package dev.team.readtoday.server.jwt.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.auth0.jwt.algorithms.Algorithm;
import dev.team.readtoday.server.jwt.domain.InvalidJwtToken;
import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenMother;
import dev.team.readtoday.server.jwt.domain.JwtTokenRepository;
import dev.team.readtoday.server.shared.domain.UserId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class Auth0JwtTokenRepositoryTest {

  @Test
  void shouldReturnValidTokenForUserId() throws InvalidJwtToken {
    Algorithm algorithm = Algorithm.HMAC256("someSecret");
    JwtTokenRepository repository = new Auth0JwtTokenRepository(algorithm);
    UserId userId = UserId.random();

    JwtToken token = repository.getForUserId(userId);

    assertEquals(userId, repository.validateAndGetUserId(token));
  }

  @Test
  void shouldThrowExceptionIfInvalidToken() {
    Algorithm algorithm = Algorithm.HMAC256("someSecret");
    JwtTokenRepository repository = new Auth0JwtTokenRepository(algorithm);
    JwtToken invalidToken = JwtTokenMother.random();

    assertThrows(InvalidJwtToken.class, () -> repository.validateAndGetUserId(invalidToken));
  }
}
