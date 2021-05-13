package dev.team.readtoday.client.usecase.customlist.get.publications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import dev.team.readtoday.client.model.Publication;
import dev.team.readtoday.client.usecase.customlist.get.publications.evets.CustomListGetPublicationsEvent;
import dev.team.readtoday.client.usecase.customlist.get.publications.evets.CustomListGetPublicationsFailedEvent;
import dev.team.readtoday.client.usecase.customlist.get.publications.evets.CustomListGetPublicationsSuccessfulEvent;
import dev.team.readtoday.client.usecase.customlist.get.publications.messages.CustomListGetPublicationsResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListGetPublicationsListenerTest {

  @Test
  void shoulGetPublicationsFromCustomListSuccessfullEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("custom-list/get")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);

    Collection<Publication> publications = new ArrayList<Publication>();
    publications
        .add(new Publication("Id", "Title", "Description", OffsetDateTime.now(), "Link", null));
    CustomListGetPublicationsResponse entity = new CustomListGetPublicationsResponse(publications);

    when(response.getEntity(CustomListGetPublicationsResponse.class)).thenReturn(entity);

    EventBus eventBus = mock(EventBus.class);
    CustomListGetPublicationsListener listener =
        new CustomListGetPublicationsListener(eventBus, factory);

    // When
    listener.onCustomListGetPublicationRequest(mock(CustomListGetPublicationsEvent.class));
    // Then
    var eventCaptor = ArgumentCaptor.forClass(CustomListGetPublicationsSuccessfulEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), CustomListGetPublicationsSuccessfulEvent.class);
  }

  @Test
  void shouldGetPublicationsFromCustomListFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("custom-list/get")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);

    String reason = "Some reason.";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    CustomListGetPublicationsListener listener =
        new CustomListGetPublicationsListener(eventBus, factory);

    // When
    listener.onCustomListGetPublicationRequest(mock(CustomListGetPublicationsEvent.class));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(CustomListGetPublicationsFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
    assertEquals(event.getClass(), CustomListGetPublicationsFailedEvent.class);
  }
}
