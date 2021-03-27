package dev.team.readtoday.server.shared.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.auth0.jwt.algorithms.Algorithm;
import dev.team.readtoday.server.user.domain.UserId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class JwtTokenManagerTest {

  @Test
  void shouldReturnValidTokenForUserId() throws InvalidJwtToken {
    Algorithm algorithm = Algorithm.HMAC256("someSecret");
    JwtTokenManager manager = new JwtTokenManager(algorithm);
    String userId = UserId.random().toString();

    String token = manager.getForUserId(userId);

    assertEquals(userId, manager.validateAndGetUserId(token));
  }
}
