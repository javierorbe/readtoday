package dev.team.readtoday.client.usecase.auth.signin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.eventbus.EventBus;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SignInRequestListenerTest {

  @Test
  void shouldPostSuccessfulSignInEventIfSucceeded() {
    EventBus eventBus = mock(EventBus.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    SignInRequestListener listener = new SignInRequestListener(eventBus, requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    String jwtToken = "someJwtToken";
    when(response.getEntity(String.class)).thenReturn(jwtToken);
    when(response.isStatusOk()).thenReturn(true);

    listener.signIn(new SignInRequestReadyEvent("someAccessToken"));

    ArgumentCaptor<SuccessfulSignInEvent> eventCaptor = ArgumentCaptor.forClass(SuccessfulSignInEvent.class);
    verify(eventBus, times(1)).post(eventCaptor.capture());
    assertEquals(jwtToken, eventCaptor.getValue().getJwtToken());
  }
}
