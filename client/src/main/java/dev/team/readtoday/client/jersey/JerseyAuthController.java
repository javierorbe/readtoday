package dev.team.readtoday.client.jersey;

import dev.team.readtoday.client.view.auth.AuthController;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public final class JerseyAuthController implements AuthController {

  private final WebTarget signUpTarget;
  private final WebTarget singInTarget;

  public JerseyAuthController(WebTarget baseTarget) {
    signUpTarget = baseTarget.path("/auth/signup");
    singInTarget = baseTarget.path("/auth/singin");
  }

  @Override
  public String signUp(String googleOauthToken, String username) {
    SignUpRequest request = new SignUpRequest(googleOauthToken, username);
    Response response = signUpTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
    return response.readEntity(String.class);
  }

  @Override
  public String signIn(String googleOauthToken) {
    // TODO: Implement method.
    SingInRequest request = new SingInRequest(googleOauthToken);
    Response response = singInTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
    return response.readEntity(String.class);
  }
}
