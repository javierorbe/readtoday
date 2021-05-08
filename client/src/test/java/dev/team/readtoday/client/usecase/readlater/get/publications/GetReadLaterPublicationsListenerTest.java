package dev.team.readtoday.client.usecase.readlater.get.publications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;

@TestMethodOrder(MethodOrderer.Random.class)
public class GetReadLaterPublicationsListenerTest {

  @Test
  void shouldGetReadLaterPublicationsSuccesfulEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/readlater")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);

    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);

    GetReadLaterPublicationsResponse entity = new GetReadLaterPublicationsResponse();
    Collection<PublicationResponse> list = new ArrayList<PublicationResponse>();
    list.add(new PublicationResponse());
    entity.setPublications(list);

    when(response.getEntity(GetReadLaterPublicationsResponse.class)).thenReturn(entity);

    EventBus eventBus = mock(EventBus.class);
    GetReadLaterPublicationsListener listener =
        new GetReadLaterPublicationsListener(eventBus, factory);

    // When
    listener.onPublicationGetRequest(new GetReadLaterPublicationsEvent());

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SuccesfulGetReadLaterPublicationsEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SuccesfulGetReadLaterPublicationsEvent.class);
  }

  @Test
  void shouldGetReadLaterPublicationsFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/readlater")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String reason = "Some reason.";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    GetReadLaterPublicationsListener listener =
        new GetReadLaterPublicationsListener(eventBus, factory);

    // When
    listener.onPublicationGetRequest(new GetReadLaterPublicationsEvent());

    // Then
    var eventCaptor = ArgumentCaptor.forClass(FailedGetReadLaterPublicationsEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
