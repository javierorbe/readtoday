package dev.team.readtoday.client.auth;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public final class AuthRequestListener {

  private final EventBus eventBus;
  private final WebTarget signUpTarget;

  public AuthRequestListener(EventBus eventBus, WebTarget baseTarget) {
    this.eventBus = eventBus;
    signUpTarget = baseTarget.path("/auth/signup");
  }

  @Subscribe
  public void signUp(SignUpRequestReadyEvent event) {
    SignUpRequest request = new SignUpRequest(event.getAccessToken(), event.getUsername());
    Response response = signUpTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));

    String jwtToken = response.readEntity(String.class);
    eventBus.post(new SuccessfulSignUpEvent(jwtToken));
  }
}
