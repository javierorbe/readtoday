package dev.team.readtoday.server.user.infrastructure.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.ws.rs.Path;

@Path("/auth/singin")
public final class SingInController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SingInController.class);

  // TODO: undo the commented section once the class SignInUser is created and implemented

  /*
   * @Inject private SingInUser signInUser;
   * 
   * @Inject private JwtTokenManager jwtTokenManager;
   * 
   * @POST
   * 
   * @Consumes(MediaType.APPLICATION_JSON)
   * 
   * @Produces(MediaType.APPLICATION_JSON) public Response singIn(SingInRequest request) {
   * LOGGER.trace("Recived sing in request");
   * 
   * try { User user = singInUser.signIn(new AuthToken(request.getToken())); String jwtToken =
   * jwtTokenManager.getForUserId(user.getId().toString());
   * 
   * LOGGER.debug("Succesful sign in"); return Response.ok(jwtToken).build(); } catch
   * (AuthProcessFailed e) { LOGGER.debug("Sign in failed.", e); return
   * Response.status(Response.Status.UNAUTHORIZED).build(); } catch (RuntimeException e) {
   * LOGGER.debug("Other exception", e); return
   * Response.status(Response.Status.BAD_REQUEST).build(); } }
   */

}
