package dev.team.readtoday.server.shared.infrastructure.controller;

import dev.team.readtoday.server.shared.domain.UserId;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response.StatusType;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

public class BaseController {

  private SecurityContext securityContext;

  @Context
  final void setSecurityContext(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  protected static Response response(Status status) {
    return Response.status(status).build();
  }

  protected static Response response(StatusType status, String message) {
    return Response.status(status.getStatusCode(), message).build();
  }

  protected final UserId getRequestUserId() {
    Principal principal = securityContext.getUserPrincipal();
    String userId = principal.getName();
    return UserId.fromString(userId);
  }
}
