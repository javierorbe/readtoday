package dev.team.readtoday.client.usecase.category.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesFailedEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesSuccessfullyEvent;
import dev.team.readtoday.client.usecase.category.search.messages.AllCategoryResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategorySearchAllListenerTest {

  @Test
  void shouldPostSearchAllCategoriesSuccessfullyEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/categories")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    AllCategoryResponse entity = mock(AllCategoryResponse.class);
    when(response.getEntity(AllCategoryResponse.class)).thenReturn(entity);
    ImmutableCollection<Category> categories = ImmutableSet.of();
    when(entity.toCategoriesCollection()).thenReturn(categories);
    EventBus eventBus = mock(EventBus.class);
    CategorySearchAllListener listener = new CategorySearchAllListener(eventBus, factory);

    // When
    listener.onSearchAllCategoriesRequestReceived(new SearchAllCategoriesEvent());

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchAllCategoriesSuccessfullyEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SearchAllCategoriesSuccessfullyEvent.class);

    var categoriesParam = event.getCategories();
    assertEquals(categoriesParam.size(), categories.size());
  }

  @Test
  void shouldPostSearchAllCategoriesFailedEvent() {
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/categories")).thenReturn(requestBuilder);
    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String reason = "Bad Request";
    when(response.getStatusReason()).thenReturn(reason);
    EventBus eventBus = mock(EventBus.class);
    CategorySearchAllListener listener = new CategorySearchAllListener(eventBus, factory);

    // When
    listener.onSearchAllCategoriesRequestReceived(new SearchAllCategoriesEvent());

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SearchAllCategoriesFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());

    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SearchAllCategoriesFailedEvent.class);

    assertEquals(reason, event.getReason());
  }
}
