package dev.team.readtoday.client.oauth;

import dev.team.readtoday.client.view.auth.AuthController;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/oauth")
public final class AuthResponseController {

  @Inject
  private AuthController authController;

  @Inject
  private JwtTokenReceiver jwtTokenReceiver;

  @Inject
  private AuthInfoProvider authInfoProvider;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String receiveSignUpToken(@QueryParam("code") String token) {
    String jwtToken = switch (authInfoProvider.getAuthProcess()) {
      case SIGN_UP -> {
        String username = authInfoProvider.getUsername();
        yield authController.signUp(token, username);
      }
      case SIGN_IN -> authController.signIn(token);
    };

    jwtTokenReceiver.receiveJwtToken(jwtToken);

    return "OK! Check the app.";
  }
}
