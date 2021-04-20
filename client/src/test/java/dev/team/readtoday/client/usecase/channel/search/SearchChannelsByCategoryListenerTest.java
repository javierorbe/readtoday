package dev.team.readtoday.client.usecase.channel.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryFailedEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategorySuccessfullyEvent;
import dev.team.readtoday.client.usecase.channel.search.messages.ChannelsByCategoryResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class SearchChannelsByCategoryListenerTest {

  @Test
  void shouldPostSearchChannelsByCategorySuccessfullyEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/channels")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    String categoryName = "some category name";
    when(requestBuilder.withParam("categoryName", categoryName)).thenReturn(requestBuilder);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    ChannelsByCategoryResponse entity = mock(ChannelsByCategoryResponse.class);
    when(response.getEntity(ChannelsByCategoryResponse.class)).thenReturn(entity);
    ImmutableCollection<Channel> channels = ImmutableSet.of();
    when(entity.toChannelCollection()).thenReturn(channels);
    EventBus eventBus = mock(EventBus.class);
    SearchChannelsByCategoryListener listener = new SearchChannelsByCategoryListener(eventBus,
        factory);

    // When
    listener.onSearchChannelsByCategory(new SearchChannelsByCategoryEvent(categoryName));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchChannelsByCategorySuccessfullyEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SearchChannelsByCategorySuccessfullyEvent.class);

    var channelsParam = event.getChannels();
    assertEquals(channelsParam.size(), channels.size());
  }

  @Test
  void shouldPostSearchChannelsByCategoryFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/channels")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    String categoryName = "some category name";
    when(requestBuilder.withParam("categoryName", categoryName)).thenReturn(requestBuilder);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String reason = "Bad Request";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    SearchChannelsByCategoryListener listener = new SearchChannelsByCategoryListener(eventBus,
        factory);

    // When
    listener.onSearchChannelsByCategory(new SearchChannelsByCategoryEvent(categoryName));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchChannelsByCategoryFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SearchChannelsByCategoryFailedEvent.class);

    assertEquals(reason, event.getReason());
  }
}
