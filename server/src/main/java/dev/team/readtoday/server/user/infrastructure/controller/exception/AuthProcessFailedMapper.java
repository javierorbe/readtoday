package dev.team.readtoday.server.user.infrastructure.controller.exception;

import dev.team.readtoday.server.user.application.profile.ProfileFetchingFailed;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public final class AuthProcessFailedMapper implements ExceptionMapper<ProfileFetchingFailed> {

  @Override
  public Response toResponse(ProfileFetchingFailed exception) {
    return Response.status(Status.UNAUTHORIZED).build();
  }
}
