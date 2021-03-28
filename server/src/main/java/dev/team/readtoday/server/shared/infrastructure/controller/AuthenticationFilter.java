package dev.team.readtoday.server.shared.infrastructure.controller;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresAuth
@Provider
@Priority(Priorities.AUTHENTICATION)
public final class AuthenticationFilter implements ContainerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

  private static final String REALM = "auth";
  private static final String AUTHENTICATION_SCHEME = "Bearer";

  @Inject
  private JwtTokenManager jwtTokenManager;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    // Get the Authorization header from the request
    String authorizationHeader =
        requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    // Validate the Authorization header
    if (!isTokenBasedAuthentication(authorizationHeader)) {
      abortWithUnauthorized(requestContext);
      return;
    }

    try {
      String token = authorizationHeader
          .substring(AUTHENTICATION_SCHEME.length()).trim();

      String userId = jwtTokenManager.validateAndGetUserId(token);

      SecurityContext securityContext = requestContext.getSecurityContext();
      requestContext.setSecurityContext(new CustomSecurityContext(userId, securityContext));

      LOGGER.trace("Successfully passed authorization filter.");
    } catch (InvalidJwtToken e) {
      LOGGER.trace("Invalid JWT token on authorization filter.", e);
      abortWithUnauthorized(requestContext);
    }
  }

  private static boolean isTokenBasedAuthentication(String authorizationHeader) {
    return (authorizationHeader != null) && authorizationHeader.toLowerCase()
        .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + ' ');
  }

  private static void abortWithUnauthorized(ContainerRequestContext requestContext) {
    requestContext.abortWith(
        Response.status(Response.Status.UNAUTHORIZED)
            .header(HttpHeaders.WWW_AUTHENTICATE,
                AUTHENTICATION_SCHEME + " realm=\"" + REALM + '"')
            .build());
  }

  private record CustomSecurityContext(String userId,
                                       SecurityContext securityContext)
      implements SecurityContext {

    @Override
    public Principal getUserPrincipal() {
      return () -> userId;
    }

    @Override
    public boolean isUserInRole(String role) {
      return true;
    }

    @Override
    public boolean isSecure() {
      return securityContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
      return AUTHENTICATION_SCHEME;
    }
  }
}
