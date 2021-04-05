package dev.team.readtoday.client.usecase.auth.signup;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

public final class SignUpRequestListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SignUpRequestListener(EventBus eventBus, HttpRequestBuilder requestBuilder) {
    this.eventBus = eventBus;
    this.requestBuilder = requestBuilder;
  }

  @Subscribe
  public void signUp(SignUpRequestReadyEvent event) {
    SignUpRequest request = new SignUpRequest(event.getAccessToken(), event.getUsername());
    HttpResponse response = requestBuilder.post(request);

    if (response.isStatusCreated()) {
      String jwtToken = response.getEntity(String.class);
      eventBus.post(new SuccessfulSignUpEvent(jwtToken));
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new SignUpFailedEvent(reason));
    }
  }
}
