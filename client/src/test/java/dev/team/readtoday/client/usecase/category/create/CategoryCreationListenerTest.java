package dev.team.readtoday.client.usecase.category.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.category.create.events.CategoryCreationEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategoryCreationFailedEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategorySuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.category.create.messages.CategoryCreationRequest;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryCreationListenerTest {

  @Test
  void shouldPostCategorySuccessfullyCreatedEventIfSucceeded() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/categories")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(true);
    EventBus eventBus = mock(EventBus.class);
    CategoryCreationListener listener = new CategoryCreationListener(eventBus, factory);
    CategoryCreationRequest request = mock(CategoryCreationRequest.class);

    // When
    listener.onCategoryCreationRequestReceived(new CategoryCreationEvent(request));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(CategorySuccessfullyCreatedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), CategorySuccessfullyCreatedEvent.class);
  }

  @Test
  void shouldPostCategoryCreationFailedEventIfFailed() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/categories")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusCreated()).thenReturn(false);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    CategoryCreationListener listener = new CategoryCreationListener(eventBus, factory);
    CategoryCreationRequest request = mock(CategoryCreationRequest.class);

    // When
    listener.onCategoryCreationRequestReceived(new CategoryCreationEvent(request));

    // Then
    var eventCaptor = ArgumentCaptor.forClass(CategoryCreationFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(reason, event.getReason());
  }
}
