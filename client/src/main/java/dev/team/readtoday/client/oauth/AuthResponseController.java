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
    String jwtToken;

    switch (authInfoProvider.getAuthProcess()) {
      case SIGN_UP -> jwtToken = authController.signUp(token, authInfoProvider.getUsername());
      case SIGN_IN -> jwtToken = authController.signIn(token);
      default -> throw new IllegalStateException(
          "Unexpected value: " + authInfoProvider.getAuthProcess());
    };

    jwtTokenReceiver.receiveJwtToken(jwtToken);

    return "OK! Check the app.";
  }
}
