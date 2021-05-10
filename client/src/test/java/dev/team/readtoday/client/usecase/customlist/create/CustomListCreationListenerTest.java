package dev.team.readtoday.client.usecase.customlist.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreatedSuccessfullyEvent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreationEvent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreationFailedEvent;
import dev.team.readtoday.client.usecase.customlist.create.messages.CustomListCreationRequest;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public final class CustomListCreationListenerTest {

  private HttpRequestBuilderFactory factory;
  private HttpResponse response;
  private CustomListCreationEvent createEvent;
  private EventBus eventBus;
  private final String title = "CustomListTitle123";

  @BeforeEach
  void basicConf() {
    // Create Http path + auth
    factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/custom-list")).thenReturn(requestBuilder);
    response = mock(HttpResponse.class);
    eventBus = mock(EventBus.class);

    // Create edit event
    CustomListCreationRequest request = new CustomListCreationRequest(title);
    createEvent = new CustomListCreationEvent(request);

    // Put http request
    when(requestBuilder.post(createEvent.getRequest())).thenReturn(response);
  }

  @Test
  @DisplayName("CustomListCreatedSuccessfully event must be triggered")
  void shouldPostCustomListCreatedSuccessfully() {
    when(response.isStatusCreated()).thenReturn(true);

    CustomListCreationListener listener = new CustomListCreationListener(eventBus, factory);

    listener.onCustomListCreationRequestReceived(createEvent);

    // Assertions
    assertEquals(createEvent.getRequest().getTitle(), title);
    var eventCaptor = ArgumentCaptor.forClass(CustomListCreatedSuccessfullyEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), CustomListCreatedSuccessfullyEvent.class);
  }

  @Test
  @DisplayName("CustomListCreationFailed event must be triggered")
  void shouldPostCustomListCreationFailed() {
    // Post request + response ok + call listener
    when(response.isStatusCreated()).thenReturn(false);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);

    CustomListCreationListener listener = new CustomListCreationListener(eventBus, factory);

    listener.onCustomListCreationRequestReceived(createEvent);

    // Assertions
    assertEquals(createEvent.getRequest().getTitle(), title);
    var eventCaptor = ArgumentCaptor.forClass(CustomListCreationFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
