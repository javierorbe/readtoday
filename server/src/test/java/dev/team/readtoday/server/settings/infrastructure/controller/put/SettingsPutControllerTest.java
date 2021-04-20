package dev.team.readtoday.server.settings.infrastructure.controller.put;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.settings.application.update.UpdateSettingsCommand;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import java.time.ZoneId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SettingsPutControllerTest {

  @Test
  void shouldReturnOkStatusIfSucceeded() {
    // Given
    CommandBus commandBus = mock(CommandBus.class);
    SettingsPutController controller = new SettingsPutController(commandBus);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    String userId = UserId.random().toString();
    when(securityContext.getUserPrincipal()).thenReturn(() -> userId);

    SettingsRequest request = new SettingsRequest();

    // When
    Response response = controller.updateSettings(request);

    // Then
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldDispatchUpdateSettingsCommandWithReceivedData() {
    // Given
    CommandBus commandBus = mock(CommandBus.class);
    SettingsPutController controller = new SettingsPutController(commandBus);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    String userId = UserId.random().toString();
    when(securityContext.getUserPrincipal()).thenReturn(() -> userId);

    SettingsRequest request = new SettingsRequest();
    String zoneId = getSampleZoneId();
    String notificationPref = "weekly";
    request.setZoneId(zoneId);
    request.setNotificationPref(notificationPref);

    // When
    controller.updateSettings(request);

    // Then
    var commandCaptor = ArgumentCaptor.forClass(UpdateSettingsCommand.class);
    verify(commandBus).dispatch(commandCaptor.capture());
    UpdateSettingsCommand command = commandCaptor.getValue();

    assertEquals(userId, command.getUserId());
    assertEquals(zoneId, command.getZoneId());
    assertEquals(notificationPref, command.getNotificationPreference());
  }

  private String getSampleZoneId() {
    return ZoneId.systemDefault().getId();
  }
}
