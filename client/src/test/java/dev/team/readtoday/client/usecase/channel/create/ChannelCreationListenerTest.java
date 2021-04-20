package dev.team.readtoday.client.usecase.channel.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.channel.create.events.ChannelCreationEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelCreationFailedEvent;
import dev.team.readtoday.client.usecase.channel.create.events.ChannelSuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.channel.create.messages.ChannelCreationRequest;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class ChannelCreationListenerTest {

  @Test
  void shouldPostChannelSuccessfullyCreatedEventIfSucceeded() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/channels")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);
    ChannelCreationListener listener = new ChannelCreationListener(eventBus, factory);
    ChannelCreationRequest request = mock(ChannelCreationRequest.class);

    // When
    listener.onChannelCreationRequestReceived(new ChannelCreationEvent(request));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(ChannelSuccessfullyCreatedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), ChannelSuccessfullyCreatedEvent.class);
  }

  @Test
  void shouldPostChannelCreationFailedEventIfFailed() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/channels")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(false);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    ChannelCreationListener listener = new ChannelCreationListener(eventBus, factory);
    ChannelCreationRequest request = mock(ChannelCreationRequest.class);

    // When
    listener.onChannelCreationRequestReceived(new ChannelCreationEvent(request));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(ChannelCreationFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
