package dev.team.readtoday.client.usecase.customlist.add;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import dev.team.readtoday.client.usecase.customlist.add.events.CustomListAddEvent;
import dev.team.readtoday.client.usecase.customlist.add.events.CustomListAddFailedEvent;
import dev.team.readtoday.client.usecase.customlist.add.events.CustomListAddSuccesfulEvent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListAddListenerTest {

  @Test
  void shouldAddPublicationToCustomListSuccesfullyEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("custom-list/add")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);
    CustomListAddListener listener = new CustomListAddListener(eventBus, factory);

    // When
    listener.onCustomListAddRequestReceived(mock(CustomListAddEvent.class));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(CustomListAddSuccesfulEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), CustomListAddSuccesfulEvent.class);
  }

  @Test
  void shouldAddPublicationToCustomListFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("custom-list/add")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String reason = "Bad Request";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    CustomListAddListener listener = new CustomListAddListener(eventBus, factory);

    // When
    listener.onCustomListAddRequestReceived(mock(CustomListAddEvent.class));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(CustomListAddFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), CustomListAddFailedEvent.class);
    assertEquals(reason, event.getReason());
  }
}
