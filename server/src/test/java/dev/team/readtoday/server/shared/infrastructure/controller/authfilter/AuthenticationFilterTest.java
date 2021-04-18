package dev.team.readtoday.server.shared.infrastructure.controller.authfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.jwt.application.validate.GetUserForToken;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class AuthenticationFilterTest {

  @Test
  void shouldAbortIfAuthorizationHeaderIsNull() {
    // Given
    GetUserForToken getUserForToken = mock(GetUserForToken.class);
    AuthenticationFilter filter = new AuthenticationFilter(getUserForToken);

    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
        .thenReturn(null);

    // When
    filter.filter(requestContext);

    // Then
    var responseCaptor = ArgumentCaptor.forClass(Response.class);
    verify(requestContext).abortWith(responseCaptor.capture());
    Response response = responseCaptor.getValue();
    assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldAbortIfIsNotTokenBasedAuthentication() {
    // Given
    GetUserForToken getUserForToken = mock(GetUserForToken.class);
    AuthenticationFilter filter = new AuthenticationFilter(getUserForToken);

    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
        .thenReturn("someInvalidAuthorizationHeader");

    // When
    filter.filter(requestContext);

    // Then
    var responseCaptor = ArgumentCaptor.forClass(Response.class);
    verify(requestContext).abortWith(responseCaptor.capture());
    Response response = responseCaptor.getValue();
    assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
  }
}
