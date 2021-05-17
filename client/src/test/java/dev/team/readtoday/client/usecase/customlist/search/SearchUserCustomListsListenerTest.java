package dev.team.readtoday.client.usecase.customlist.search;

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
import dev.team.readtoday.client.usecase.customlist.search.event.SearchUserCustomListsEvent;
import dev.team.readtoday.client.usecase.customlist.search.event.SearchUserCustomListsFailedEvent;
import dev.team.readtoday.client.usecase.customlist.search.event.SearchUserCustomListsSuccefulEvent;
import dev.team.readtoday.client.usecase.customlist.search.messages.CustomListResponse;
import dev.team.readtoday.client.usecase.customlist.search.messages.SearchUserCustomListsResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

@TestMethodOrder(MethodOrderer.Random.class)
public class SearchUserCustomListsListenerTest {

  @Test
  void shoulGetPublicationsFromCustomListSuccessfullEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("custom-list/search")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);

    Collection<CustomListResponse> listCollection = new ArrayList<>();

    SearchUserCustomListsResponse entity = new SearchUserCustomListsResponse(listCollection);

    when(response.getEntity(SearchUserCustomListsResponse.class)).thenReturn(entity);

    EventBus eventBus = mock(EventBus.class);
    SearchUserCustomListsListener listener = new SearchUserCustomListsListener(eventBus, factory);

    // When
    listener.onSearchUserCustomListsRequest(mock(SearchUserCustomListsEvent.class));
    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchUserCustomListsSuccefulEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SearchUserCustomListsSuccefulEvent.class);
  }

  @Test
  void shouldGetPublicationsFromCustomListFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("custom-list/search")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);

    String reason = "Some reason.";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    SearchUserCustomListsListener listener = new SearchUserCustomListsListener(eventBus, factory);

    // When
    listener.onSearchUserCustomListsRequest(mock(SearchUserCustomListsEvent.class));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchUserCustomListsFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
    assertEquals(event.getClass(), SearchUserCustomListsFailedEvent.class);
  }
}
