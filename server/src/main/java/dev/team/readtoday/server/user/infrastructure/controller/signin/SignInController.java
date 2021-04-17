package dev.team.readtoday.server.user.infrastructure.controller.signin;

import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.JwtTokenManager;
import dev.team.readtoday.server.user.application.AccessToken;
import dev.team.readtoday.server.user.application.AuthProcessFailed;
import dev.team.readtoday.server.user.application.SignInUser;
import dev.team.readtoday.server.user.domain.NonExistingUser;
import dev.team.readtoday.server.user.domain.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/auth/signin")
public final class SignInController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

  private final SignInUser signInUser;
  private final JwtTokenManager jwtTokenManager;

  @Autowired
  public SignInController(SignInUser signInUser, JwtTokenManager jwtTokenManager) {
    this.signInUser = signInUser;
    this.jwtTokenManager = jwtTokenManager;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signIn(SignInRequest request) {
    LOGGER.trace("Received sign in request.");

    try {
      User user = signInUser.signIn(new AccessToken(request.getAccessToken()));
      String jwtToken = jwtTokenManager.getForUserId(user.getId().toString());

      LOGGER.debug("Successful sign in.");
      return Response.ok(jwtToken).build();
    } catch (AuthProcessFailed e) {
      LOGGER.debug("Sign in failed.", e);
      return response(Status.UNAUTHORIZED);
    } catch (NonExistingUser e) {
      LOGGER.debug("Sign in failed.", e);
      return response(Status.NOT_FOUND);
    }
  }
}
