package dev.team.readtoday.client.usecase.subscription.subscribe;

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
public class SubscriptionListenerTest {

  @Test
  void shouldPostSubscriptionSuccesfulEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/subscribe")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    String channelId = "a channel Id";
    when(requestBuilder.withParam("channelId", channelId)).thenReturn(requestBuilder);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);
    SubscriptionListener listener = new SubscriptionListener(eventBus, factory);

    // When
    listener.onSubscriptionRequested(new SubscriptionRequestedEvent(channelId));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SuccessfulSubscriptionEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SuccessfulSubscriptionEvent.class);
  }

  @Test
  void shouldPostSuubscriptionFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/subscribe")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    String channelId = "a channel Id";
    when(requestBuilder.withParam("channelId", channelId)).thenReturn(requestBuilder);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(false);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    SubscriptionListener listener = new SubscriptionListener(eventBus, factory);

    // When
    listener.onSubscriptionRequested(new SubscriptionRequestedEvent(channelId));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SubscriptionFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
