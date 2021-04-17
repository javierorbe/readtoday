package dev.team.readtoday.server.shared.infrastructure.controller.authfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import jakarta.ws.rs.core.SecurityContext;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class CustomSecurityContextTest {

  private static final Faker FAKER = Faker.instance();

  @Test
  void shouldReturnProvidedUserIdAsUserPrincipal() {
    SecurityContext superCtx = mock(SecurityContext.class);
    String userId = UUID.randomUUID().toString();
    SecurityContext customCtx = new CustomSecurityContext(userId, superCtx);
    assertEquals(userId, customCtx.getUserPrincipal().getName());
  }

  @Test
  void shouldAlwaysReturnUserIsInRole() {
    SecurityContext superCtx = mock(SecurityContext.class);
    String userId = UUID.randomUUID().toString();
    SecurityContext customCtx = new CustomSecurityContext(userId, superCtx);
    assertTrue(customCtx.isUserInRole(FAKER.lorem().word()));
  }

  @Test
  void shouldCopyIsSecureFromSuperSecurityContext() {
    SecurityContext superCtx = mock(SecurityContext.class);
    String userId = UUID.randomUUID().toString();
    SecurityContext customCtx = new CustomSecurityContext(userId, superCtx);

    when(superCtx.isSecure()).thenReturn(true);
    assertTrue(customCtx.isSecure());

    when(superCtx.isSecure()).thenReturn(false);
    assertFalse(customCtx.isSecure());
  }

  @Test
  void shouldReturnBearerAsAuthenticationScheme() {
    SecurityContext superCtx = mock(SecurityContext.class);
    String userId = UUID.randomUUID().toString();
    SecurityContext customCtx = new CustomSecurityContext(userId, superCtx);
    assertEquals("Bearer", customCtx.getAuthenticationScheme());
  }
}
