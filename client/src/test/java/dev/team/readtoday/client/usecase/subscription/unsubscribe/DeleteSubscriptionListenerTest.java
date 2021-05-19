package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;


@TestMethodOrder(MethodOrderer.Random.class)
public class DeleteSubscriptionListenerTest {

  @Test
  void shouldDeleteSubscriptionSuccessfulEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/unsubscribe")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    Channel channel = new Channel("1", "Test", "https://www.test.com/", "Testing",
        "https://www.test.com/", new ArrayList<>());
    when(requestBuilder.withParam("channelId", channel.getId())).thenReturn(requestBuilder);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusNoContent()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);
    DeleteSubscriptionListener listener = new DeleteSubscriptionListener(eventBus, factory);

    // When
    listener.onDeleteSubscription(new DeleteSubscriptionEvent(channel));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(DeleteSubscriptionSuccessfulEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), DeleteSubscriptionSuccessfulEvent.class);
  }

  @Test
  void shouldDeleteSubscriptionFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/unsubscribe")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    Channel channel = new Channel("1", "Test", "https://www.test.com/", "Testing",
        "https://www.test.com/", new ArrayList<>());
    when(requestBuilder.withParam("channelId", channel.getId())).thenReturn(requestBuilder);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusNoContent()).thenReturn(false);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    DeleteSubscriptionListener listener = new DeleteSubscriptionListener(eventBus, factory);

    // When
    listener.onDeleteSubscription(new DeleteSubscriptionEvent(channel));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(DeleteSubscriptionFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
