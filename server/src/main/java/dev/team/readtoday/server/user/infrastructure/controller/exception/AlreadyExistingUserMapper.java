package dev.team.readtoday.server.user.infrastructure.controller.exception;

import dev.team.readtoday.server.user.domain.AlreadyExistingUser;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public final class AlreadyExistingUserMapper implements ExceptionMapper<AlreadyExistingUser> {

  @Override
  public Response toResponse(AlreadyExistingUser e) {
    return Response.status(Status.CONFLICT).build();
  }
}
