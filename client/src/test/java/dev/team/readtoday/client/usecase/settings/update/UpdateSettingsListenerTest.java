package dev.team.readtoday.client.usecase.settings.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.client.model.NotificationPreference;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import java.time.ZoneOffset;
import org.greenrobot.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class UpdateSettingsListenerTest {

  private HttpResponse response;
  private EventBus eventBus;

  private UpdateSettingsEvent event;
  private ArgumentCaptor<UpdateSettingsRequest> requestCaptor;
  private UpdateSettingsListener listener;

  @BeforeEach
  void setUp() {
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/settings")).thenReturn(requestBuilder);

    response = mock(HttpResponse.class);
    requestCaptor = ArgumentCaptor.forClass(UpdateSettingsRequest.class);
    when(requestBuilder.put(requestCaptor.capture())).thenReturn(response);

    eventBus = mock(EventBus.class);
    listener = new UpdateSettingsListener(eventBus, factory);

    event = new UpdateSettingsEvent(ZoneOffset.UTC, NotificationPreference.DAILY);
  }

  @Test
  void shouldPostSettingsSuccessfullyUpdatedIfStatusIsOk() {
    // Given
    when(response.isStatusOk()).thenReturn(true);

    // When
    listener.onUpdateSettingsRequest(event);

    // Then
    UpdateSettingsRequest request = requestCaptor.getValue();
    assertEquals(event.getZoneOffset().getId(), request.getZoneId());
    assertEquals(event.getNotificationPref().toString(), request.getNotificationPref());

    var eventCaptor = ArgumentCaptor.forClass(SettingsSuccessfullyUpdated.class);
    verify(eventBus).post(eventCaptor.capture());
    var capturedEvent = eventCaptor.getValue();
    assertNotNull(capturedEvent);
  }

  @Test
  void shouldPostFailedSettingsUpdateIfStatusIsNotOk() {
    // Given
    when(response.isStatusOk()).thenReturn(false);
    String statusReason = "someStatusReason";
    when(response.getStatusReason()).thenReturn(statusReason);

    // When
    listener.onUpdateSettingsRequest(event);

    // Then
    var eventCaptor = ArgumentCaptor.forClass(FailedSettingsUpdate.class);
    verify(eventBus).post(eventCaptor.capture());
    var capturedEvent = eventCaptor.getValue();
    assertEquals(statusReason, capturedEvent.getReason());
  }
}
