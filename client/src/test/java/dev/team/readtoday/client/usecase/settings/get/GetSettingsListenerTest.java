package dev.team.readtoday.client.usecase.settings.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
final class GetSettingsListenerTest {

  private HttpResponse response;
  private EventBus eventBus;

  private GetSettingsEvent event;
  private GetSettingsListener listener;

  @BeforeEach
  void setUp() {
    HttpRequestBuilderFactory factory = mock(HttpRequestBuilderFactory.class);
    HttpRequestBuilder requestBuilder = mock(HttpRequestBuilder.class);
    when(factory.buildWithAuth("/settings")).thenReturn(requestBuilder);

    response = mock(HttpResponse.class);
    when(requestBuilder.get()).thenReturn(response);

    eventBus = mock(EventBus.class);
    listener = new GetSettingsListener(eventBus, factory);

    event = new GetSettingsEvent();
  }

  @Test
  void shouldPostSettingsReceivedEventIfStatusIsOk() {
    // Given
    when(response.isStatusOk()).thenReturn(true);

    GetSettingsResponse entity = new GetSettingsResponse();
    ZoneOffset zoneOffset = ZoneOffset.UTC;
    entity.setZoneId(zoneOffset.getId());
    var notificationPref = NotificationPreference.DAILY;
    entity.setNotificationPref(notificationPref.toString());

    when(response.getEntity(GetSettingsResponse.class))
        .thenReturn(entity);

    // When
    listener.onGetSettings(event);

    // Then
    var eventCaptor = ArgumentCaptor.forClass(SettingsReceivedEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var capturedEvent = eventCaptor.getValue();
    assertEquals(notificationPref, capturedEvent.getNotificationPreference());
    assertEquals(zoneOffset, capturedEvent.getZoneOffset());
  }

  @Test
  void shouldPostFailedToGetSettingsEventIfStatusIsNotOk() {
    // Given
    when(response.isStatusOk()).thenReturn(false);
    String statusReason = "someStatusReason";
    when(response.getStatusReason()).thenReturn(statusReason);

    // When
    listener.onGetSettings(event);

    // Then
    var eventCaptor = ArgumentCaptor.forClass(FailedToGetSettingsEvent.class);
    verify(eventBus).post(eventCaptor.capture());
    var capturedEvent = eventCaptor.getValue();
    assertEquals(statusReason, capturedEvent.getReason());
  }
}
