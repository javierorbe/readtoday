package dev.team.readtoday.client.usecase.auth.signin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SignInRequestListenerTest {

  @Test
  void shouldRequestWithEventArguments() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signin")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    var signInRequestCaptor = ArgumentCaptor.forClass(SignInRequest.class);
    when(requestBuilder.post(signInRequestCaptor.capture())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);

    EventBus eventBus = mock(EventBus.class);
    var listener = new SignInRequestListener(eventBus, factory);

    // When
    String accessToken = "someAccessToken";
    listener.onSignInRequestReady(new SignInRequestReadyEvent(accessToken));

    // Then
    var signInRequest = signInRequestCaptor.getValue();
    assertEquals(accessToken, signInRequest.getAccessToken());
  }

  @Test
  void shouldPostSuccessfulSignInEventIfSucceeded() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signin")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    String jwtToken = "someJwtToken";
    when(response.getEntity(String.class)).thenReturn(jwtToken);

    EventBus eventBus = mock(EventBus.class);
    var listener = new SignInRequestListener(eventBus, factory);

    // When
    listener.onSignInRequestReady(new SignInRequestReadyEvent("someAccessToken"));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SuccessfulSignInEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(jwtToken, event.getJwtToken());
  }

  @Test
  void shouldPostSignInFailedEventIfFailed() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signin")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String statusReason = "someStatusReason";
    when(response.getStatusReason()).thenReturn(statusReason);

    EventBus eventBus = mock(EventBus.class);
    var listener = new SignInRequestListener(eventBus, factory);

    // When
    listener.onSignInRequestReady(new SignInRequestReadyEvent("someAccessToken"));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SignInFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(statusReason, event.getReason());
  }
}
