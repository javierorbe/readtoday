package dev.team.readtoday.server.user.infrastructure.controller;

import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.user.application.AuthProcessFailed;
import dev.team.readtoday.server.user.application.AuthToken;
import dev.team.readtoday.server.user.application.SignUpUser;
import dev.team.readtoday.server.user.domain.AlreadyExistingUser;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.Username;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/auth/signup")
public final class SignUpController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

  @Inject
  private SignUpUser signUpUser;
  @Inject
  private JwtTokenManager jwtTokenManager;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signUp(SignUpRequest request) {
    LOGGER.trace("Received sign up request.");

    try {
      User user = signUpUser.signUp(
          new AuthToken(request.getToken()),
          new Username(request.getUsername())
      );
      String jwtToken = jwtTokenManager.getForUserId(user.getId().toString());

      LOGGER.debug("Successful user sign up.");
      return Response.status(Response.Status.CREATED).entity(jwtToken).build();
    } catch (AuthProcessFailed e) {
      LOGGER.debug("Sign up failed.", e);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } catch (AlreadyExistingUser e) {
      LOGGER.debug("Sign up failed.", e);
      return Response.status(Response.Status.CONFLICT).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Sign up failed.", e);
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }
}
