package dev.team.readtoday.server.user.infrastructure.controller.signup;

import dev.team.readtoday.server.jwt.application.get.GetJwtToken;
import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.user.application.AccessToken;
import dev.team.readtoday.server.user.application.AuthProcessFailed;
import dev.team.readtoday.server.user.application.SignUpUser;
import dev.team.readtoday.server.user.domain.AlreadyExistingUser;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.Username;
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

@Path("/auth/signup")
public final class SignUpController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

  private final SignUpUser signUpUser;
  private final GetJwtToken getJwtToken;

  @Autowired
  public SignUpController(SignUpUser signUpUser, GetJwtToken getJwtToken) {
    this.signUpUser = signUpUser;
    this.getJwtToken = getJwtToken;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signUp(SignUpRequest request) {
    LOGGER.trace("Received sign up request.");

    try {
      User user = signUpUser.signUp(new AccessToken(request.getAccessToken()),
          new Username(request.getUsername()));
      JwtToken jwtToken = getJwtToken.apply(user.getId());

      LOGGER.debug("Successful user sign up.");
      return Response.status(Status.CREATED).entity(jwtToken.toString()).build();
    } catch (AuthProcessFailed e) {
      LOGGER.debug("Sign up failed.", e);
      return response(Status.UNAUTHORIZED);
    } catch (AlreadyExistingUser e) {
      LOGGER.debug("Sign up failed.", e);
      return response(Status.CONFLICT);
    }
  }
}
