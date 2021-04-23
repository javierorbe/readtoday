package dev.team.readtoday.client.usecase.subscription.publications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.ArrayList;
import java.util.Collection;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class PublicationListenerTest {

  @Test
  void shouldGetPublicationSuccesfullyEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/subscriptions/publications")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    PublicationListResponse entity = mock(PublicationListResponse.class);
    when(response.getEntity(PublicationListResponse.class)).thenReturn(entity);
    Collection<PublicationResponse> publications = new ArrayList<>();
    when(entity.getPublications()).thenReturn(publications);
    EventBus eventBus = mock(EventBus.class);
    PublicationRequestListener listener = new PublicationRequestListener(eventBus, factory);

    // When
    listener.onPublicationRequestEvent(mock(PublicationRequestEvent.class));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(PublicationRequestSuccesfulEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), PublicationRequestSuccesfulEvent.class);

    var eventPublications = event.getPublications();
    assertEquals(eventPublications.size(), publications.size());
  }

  @Test
  void shouldGetPublicationFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/subscriptions/publications")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String reason = "Bad Request";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    PublicationRequestListener listener = new PublicationRequestListener(eventBus, factory);

    // When
    listener.onPublicationRequestEvent(mock(PublicationRequestEvent.class));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(PublicationRequestFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), PublicationRequestFailedEvent.class);

    assertEquals(reason, event.getReason());
  }
}
