package dev.team.readtoday.client.usecase.auth.signup;

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
final class SignUpRequestListenerTest {

  @Test
  void shouldRequestWithEventArguments() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signup")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    var signUpRequestCaptor = ArgumentCaptor.forClass(SignUpRequest.class);
    when(requestBuilder.post(signUpRequestCaptor.capture())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(false);

    EventBus eventBus = mock(EventBus.class);
    var listener = new SignUpRequestListener(eventBus, factory);

    // When
    String accessToken = "someAccessToken";
    String username = "someUsername";
    listener.onSignUpRequestReady(new SignUpRequestReadyEvent(accessToken, username));

    // Then
    var signUpRequest = signUpRequestCaptor.getValue();
    assertEquals(username, signUpRequest.getUsername());
    assertEquals(accessToken, signUpRequest.getAccessToken());
  }

  @Test
  void shouldPostSuccessfulSignUpEventIfSucceeded() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signup")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(true);
    String jwtToken = "someJwtToken";
    when(response.getEntity(String.class)).thenReturn(jwtToken);

    EventBus eventBus = mock(EventBus.class);
    var listener = new SignUpRequestListener(eventBus, factory);

    // When
    listener.onSignUpRequestReady(new SignUpRequestReadyEvent("someAccessToken", "someUsername"));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SuccessfulSignUpEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(jwtToken, event.getJwtToken());
  }

  @Test
  void shouldPostSignUpFailedEventIfFailed() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signup")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(false);
    String statusReason = "someStatusReason";
    when(response.getStatusReason()).thenReturn(statusReason);

    EventBus eventBus = mock(EventBus.class);
    var listener = new SignUpRequestListener(eventBus, factory);

    // When
    listener.onSignUpRequestReady(new SignUpRequestReadyEvent("someAccessToken", "someUsername"));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SignUpFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(statusReason, event.getReason());
  }
}
