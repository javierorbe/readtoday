package dev.team.readtoday.client.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.subscription.search.MySubscriptionsListListener;
import dev.team.readtoday.client.usecase.subscription.search.events.MySubscriptionsListFailedEvent;
import dev.team.readtoday.client.usecase.subscription.search.events.MySubscriptionsListRequestedEvent;
import dev.team.readtoday.client.usecase.subscription.search.events.SuccessfulMySubscriptionsListEvent;
import dev.team.readtoday.client.usecase.subscription.search.messages.AllSubscriptionsResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class MySubscriptionListListenerTest {

  @Test
  void shouldGetMySubscriptionsListSuccess(){
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/subscriptions")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);
    AllSubscriptionsResponse entity = mock(AllSubscriptionsResponse.class);
    when(response.getEntity(AllSubscriptionsResponse.class)).thenReturn(entity);
    ImmutableCollection<Channel> channels = ImmutableSet.of();
    when(entity.toChannelsCollection()).thenReturn(channels);

    EventBus eventBus = mock(EventBus.class);
    MySubscriptionsListListener listener = new MySubscriptionsListListener(eventBus, factory);

    // When

    listener.onSubscriptionListRequested(new MySubscriptionsListRequestedEvent());

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SuccessfulMySubscriptionsListEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(),SuccessfulMySubscriptionsListEvent.class);
  }

  @Test
  void shouldGetMySubscriptionsListFail(){
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/subscriptions")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);
    String reason = "Failed reason";
    when(response.getStatusReason()).thenReturn(reason);
    AllSubscriptionsResponse entity = mock(AllSubscriptionsResponse.class);
    when(response.getEntity(AllSubscriptionsResponse.class)).thenReturn(entity);
    ImmutableCollection<Channel> channels = ImmutableSet.of();
    when(entity.toChannelsCollection()).thenReturn(channels);

    EventBus eventBus = mock(EventBus.class);
    MySubscriptionsListListener listener = new MySubscriptionsListListener(eventBus, factory);

    // When

    listener.onSubscriptionListRequested(new MySubscriptionsListRequestedEvent());

    // Then
    var eventCaptor = ArgumentCaptor.forClass(MySubscriptionsListFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), MySubscriptionsListFailedEvent.class);
  }

}

