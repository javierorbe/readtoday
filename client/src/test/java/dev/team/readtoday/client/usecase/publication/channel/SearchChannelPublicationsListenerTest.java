package dev.team.readtoday.client.usecase.publication.channel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.shared.GenericType;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchChannelPublicationsListenerTest {

  private static final PublicationResponseListType PUBLICATION_RESPONSE_LIST_TYPE =
      new PublicationResponseListType();

  @Test
  void shouldPostChannelPublicationsFoundIfSucceeded() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/publications")).thenReturn(requestBuilder);
    String channelId = "e53adf90-9996-4216-8e85-b2affdf5d55d";
    HttpRequestBuilder finalRequestBuilder = mock(HttpRequestBuilder.class);
    when(requestBuilder.withParam("channelId", channelId)).thenReturn(finalRequestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(finalRequestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    List<PublicationResponse> list = List.of();
    when(response.getEntity(eq(PUBLICATION_RESPONSE_LIST_TYPE))).thenReturn(list);
    EventBus eventBus = mock(EventBus.class);
    SearchChannelPublicationsListener listener = new SearchChannelPublicationsListener(eventBus, factory);

    // When
    listener.onSearchChannelPublications(new SearchChannelPublicationsEvent(channelId));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(ChannelPublicationsFoundEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    ChannelPublicationsFoundEvent event = eventCaptor.getValue();
    assertEquals(channelId, event.getChannelId());
    assertEquals(0, event.getPublications().size());
  }

  @Test
  void shouldPostSearchChannelPublicationsFailedEventIfFailed() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/publications")).thenReturn(requestBuilder);
    String channelId = "e53adf90-9996-4216-8e85-b2affdf5d55d";
    HttpRequestBuilder finalRequestBuilder = mock(HttpRequestBuilder.class);
    when(requestBuilder.withParam("channelId", channelId)).thenReturn(finalRequestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(finalRequestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String statusReason = "someStatusReason";
    when(response.getStatusReason()).thenReturn(statusReason);
    EventBus eventBus = mock(EventBus.class);
    SearchChannelPublicationsListener listener = new SearchChannelPublicationsListener(eventBus, factory);

    // When
    listener.onSearchChannelPublications(new SearchChannelPublicationsEvent(channelId));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchChannelPublicationsFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(statusReason, event.getReason());
  }

  private static final class PublicationResponseListType
      extends GenericType<List<PublicationResponse>> {

  }
}
