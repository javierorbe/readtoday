package dev.team.readtoday.client.usecase.auth.signup;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SubscribedComponent
public final class SignUpRequestListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  SignUpRequestListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.build("/auth/signup");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSignUpRequestReady(SignUpRequestReadyEvent event) {
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
