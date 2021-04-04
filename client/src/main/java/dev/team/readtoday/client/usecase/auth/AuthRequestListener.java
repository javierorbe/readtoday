package dev.team.readtoday.client.usecase.auth;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.auth.signin.SignInFailedEvent;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequest;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequestReadyEvent;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpFailedEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpRequest;
import dev.team.readtoday.client.usecase.auth.signup.SignUpRequestReadyEvent;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public final class AuthRequestListener {

  private final EventBus eventBus;
  private final WebTarget signUpTarget;
  private final WebTarget signInTarget;

  public AuthRequestListener(EventBus eventBus, WebTarget baseTarget) {
    this.eventBus = eventBus;
    signUpTarget = baseTarget.path("/auth/signup");
    signInTarget = baseTarget.path("/auth/signin");
  }

  @Subscribe
  public void signUp(SignUpRequestReadyEvent event) {
    SignUpRequest request = new SignUpRequest(event.getAccessToken(), event.getUsername());
    Response response = signUpTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));

    if (isResponseStatusCreated(response)) {
      String jwtToken = response.readEntity(String.class);
      eventBus.post(new SuccessfulSignUpEvent(jwtToken));
    } else {
      String reason = Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase();
      eventBus.post(new SignUpFailedEvent(reason));
    }
  }

  @Subscribe
  public void signIn(SignInRequestReadyEvent event) {
    SignInRequest request = new SignInRequest(event.getAccessToken());
    Response response = signInTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));

    if (isResponseStatusOk(response)) {
      String jwtToken = response.readEntity(String.class);
      eventBus.post(new SuccessfulSignInEvent(jwtToken));
    } else {
      String reason = Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase();
      eventBus.post(new SignInFailedEvent(reason));
    }
  }

  private static boolean isResponseStatusCreated(Response response) {
    return response.getStatus() == Response.Status.CREATED.getStatusCode();
  }

  private static boolean isResponseStatusOk(Response response) {
    return response.getStatus() == Response.Status.OK.getStatusCode();
  }
}
