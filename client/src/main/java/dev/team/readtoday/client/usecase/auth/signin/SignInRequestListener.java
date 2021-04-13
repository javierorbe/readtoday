package dev.team.readtoday.client.usecase.auth.signin;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

public final class SignInRequestListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SignInRequestListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.build("/auth/signin");
    eventBus.register(this);
  }

  @Subscribe
  public void signIn(SignInRequestReadyEvent event) {
    SignInRequest request = new SignInRequest(event.getAccessToken());
    HttpResponse response = requestBuilder.post(request);

    if (response.isStatusOk()) {
      String jwtToken = response.getEntity(String.class);
      eventBus.post(new SuccessfulSignInEvent(jwtToken));
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new SignInFailedEvent(reason));
    }
  }
}
