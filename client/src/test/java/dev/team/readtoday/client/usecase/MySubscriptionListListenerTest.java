package dev.team.readtoday.client.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.auth.signin.SignInRequest;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequestListener;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequestReadyEvent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.subscription.search.MySubscriptionsListListener;
import dev.team.readtoday.client.usecase.subscription.subscribe.SubscriptionRequestedEvent;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class MySubscriptionListListenerTest {

  @Test
  void shouldGetMySubscriptionsList(){
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signin")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    var signInRequestCaptor = ArgumentCaptor.forClass(SignInRequest.class);
    when(requestBuilder.post(signInRequestCaptor.capture())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);

    EventBus eventBus = mock(EventBus.class);
    var listener = new MySubscriptionsListListener(eventBus, factory);

    // When

    //listener.onSubscriptionListRequested(new SubscriptionRequestedEvent(channel));

    // Then
    var signInRequest = signInRequestCaptor.getValue();
    //assertEquals(accessToken, signInRequest.getAccessToken());
  }

}
