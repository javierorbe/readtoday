package dev.team.readtoday.server.jwt.application.validate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenMother;
import dev.team.readtoday.server.shared.domain.UserId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class GetUserForTokenQueryHandlerTest {

  @Test
  void shouldReturnUserForToken() {
    // Given
    var getUserForToken = mock(GetUserForToken.class);
    var handler = new GetUserForTokenQueryHandler(getUserForToken);

    JwtToken token = JwtTokenMother.random();
    UserId userId = UserId.random();
    when(getUserForToken.apply(token)).thenReturn(userId);

    var query = new GetUserForTokenQuery(token.toString());

    // When
    var response = handler.handle(query);

    // Then
    assertEquals(userId.toString(), response.getUserId());
  }
}
