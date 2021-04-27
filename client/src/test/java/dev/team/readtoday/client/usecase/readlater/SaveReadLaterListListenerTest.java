package dev.team.readtoday.client.usecase.readlater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class SaveReadLaterListListenerTest {
  @Test
  void shouldSaveReadLaterListSuccess(){
    // Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/readlater")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(true);

    EventBus eventBus = mock(EventBus.class);
    SaveReadLaterListListener listener = new SaveReadLaterListListener(eventBus, factory);
    ReadLaterRequest request = mock(ReadLaterRequest.class);

    // When
    listener.onReadLaterListRequested(new SaveReadLaterListRequestedEvent(request));

    //Then
    var eventCaptor = ArgumentCaptor.forClass(SuccessfulSaveReadLaterListEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SuccessfulSaveReadLaterListEvent.class);

  }

  @Test
  void shouldSaveReadLaterListFail(){
    //Given
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/readlater")).thenReturn(requestBuilder);

    HttpResponse response = mock(HttpResponse.class);
    when(requestBuilder.post(any())).thenReturn(response);
    when(response.isStatusOk()).thenReturn(false);

    EventBus eventBus = mock(EventBus.class);
    SaveReadLaterListListener listener = new SaveReadLaterListListener(eventBus, factory);
    ReadLaterRequest request = mock(ReadLaterRequest.class);

    // When
    listener.onReadLaterListRequested(new SaveReadLaterListRequestedEvent(request));

    //Then
    var eventCaptor = ArgumentCaptor.forClass(SaveReadLaterListFailedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var event = eventCaptor.getValue();
    assertEquals(event.getClass(), SaveReadLaterListFailedEvent.class);
  }
}
