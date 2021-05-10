package dev.team.readtoday.client.usecase.channel.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.channel.edit.events.ChannelEditedSuccessfully;
import dev.team.readtoday.client.usecase.channel.edit.events.ChannelEditionFailed;
import dev.team.readtoday.client.usecase.channel.edit.events.EditChannelEvent;
import dev.team.readtoday.client.usecase.channel.edit.messages.EditChannelRequest;
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
public final class ChannelEditListenerTest {

  private HttpRequestBuilderFactory factory;
  private HttpResponse response;
  private EditChannelEvent editEvent;

  @BeforeEach
  void basicConf() {
    // Create Http path + auth
    factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/channels")).thenReturn(requestBuilder);
    response = mock(HttpResponse.class);

    // Create edit event
    EditChannelRequest request = mock(EditChannelRequest.class);
    String channelId = "k19013k";
    editEvent = new EditChannelEvent(channelId, request);

    // Put http request
    when(requestBuilder.put(channelId, request)).thenReturn(response);
  }

  @Test
  @DisplayName("ChannelEditedSuccessfully event must be triggered")
  void shouldPostChannelEditedSuccessfully() {
    // Put request + response ok + call listener

    when(response.isStatusOk()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);

    ChannelEditListener listener = new ChannelEditListener(eventBus, factory);

    listener.onChannelEdit(editEvent);

    // Assertions
    var eventCaptor = ArgumentCaptor.forClass(ChannelEditedSuccessfully.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), ChannelEditedSuccessfully.class);
  }

  @Test
  @DisplayName("ChannelEditionFailed event must be triggered")
  void shouldPostChannelEditionFailed() {
    // Put request + response ok + call listener
    when(response.isStatusOk()).thenReturn(false);
    EventBus eventBus = mock(EventBus.class);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);

    ChannelEditListener listener = new ChannelEditListener(eventBus, factory);

    listener.onChannelEdit(editEvent);

    // Assertions
    var eventCaptor = ArgumentCaptor.forClass(ChannelEditionFailed.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
