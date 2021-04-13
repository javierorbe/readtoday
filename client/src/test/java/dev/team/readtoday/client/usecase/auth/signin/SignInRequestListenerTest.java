package dev.team.readtoday.client.usecase.auth.signin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.eventbus.EventBus;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SignInRequestListenerTest {

  @Test
  void shouldPostSuccessfulSignInEventIfSucceeded() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.build("/auth/signin")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    String jwtToken = "someJwtToken";
    when(response.getEntity(String.class)).thenReturn(jwtToken);
    when(response.isStatusOk()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);
    SignInRequestListener listener = new SignInRequestListener(eventBus, factory);

    // When
    listener.signIn(new SignInRequestReadyEvent("someAccessToken"));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SuccessfulSignInEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    assertEquals(jwtToken, eventCaptor.getValue().getJwtToken());
  }
}
