package dev.team.readtoday.server.user.infrastructure.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.user.application.AuthProcessFailed;
import dev.team.readtoday.server.user.application.AuthToken;
import dev.team.readtoday.server.user.application.SignInUser;
import dev.team.readtoday.server.user.domain.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/auth/singin")
public final class SingInController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SingInController.class);

  @Inject
  private SignInUser signInUser;

  @Inject
  private JwtTokenManager jwtTokenManager;

  @POST

  @Consumes(MediaType.APPLICATION_JSON)

  @Produces(MediaType.APPLICATION_JSON)
  public Response singIn(SingInRequest request) {
    LOGGER.trace("Recived sing in request");

    try {
      User user = signInUser.SignIn(new AuthToken(request.getToken()));
      String jwtToken = jwtTokenManager.getForUserId(user.getId().toString());

      LOGGER.debug("Succesful sign in");
      return Response.ok(jwtToken).build();
    } catch (AuthProcessFailed e) {
      LOGGER.debug("Sign in failed.", e);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Other exception", e);
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

}
