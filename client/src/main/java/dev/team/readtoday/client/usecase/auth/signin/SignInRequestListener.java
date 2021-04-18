package dev.team.readtoday.client.usecase.auth.signin;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SubscribedComponent
public final class SignInRequestListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  SignInRequestListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.build("/auth/signin");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSignInRequestReady(SignInRequestReadyEvent event) {
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
