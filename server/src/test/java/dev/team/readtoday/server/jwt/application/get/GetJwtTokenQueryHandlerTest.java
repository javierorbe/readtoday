package dev.team.readtoday.server.jwt.application.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenMother;
import dev.team.readtoday.server.shared.domain.UserId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class GetJwtTokenQueryHandlerTest {

  @Test
  void shouldResponseWithJwtToken() {
    // Given
    var getJwtToken = mock(GetJwtToken.class);
    var handler = new GetJwtTokenQueryHandler(getJwtToken);

    UserId userId = UserId.random();
    JwtToken token = JwtTokenMother.random();
    when(getJwtToken.apply(userId)).thenReturn(token);

    var query = new GetJwtTokenQuery(userId.toString());

    // When
    var response = handler.handle(query);

    // Then
    assertEquals(token.toString(), response.getJwtToken());
  }
}
