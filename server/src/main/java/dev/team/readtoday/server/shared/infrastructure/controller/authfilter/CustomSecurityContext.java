package dev.team.readtoday.server.shared.infrastructure.controller.authfilter;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

final class CustomSecurityContext implements SecurityContext {

  private static final String AUTHENTICATION_SCHEME = "Bearer";

  private final String userId;
  private final SecurityContext securityContext;

  CustomSecurityContext(String userId, SecurityContext securityContext) {
    this.userId = userId;
    this.securityContext = securityContext;
  }

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
