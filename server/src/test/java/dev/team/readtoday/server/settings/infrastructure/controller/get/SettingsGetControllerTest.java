package dev.team.readtoday.server.settings.infrastructure.controller.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.settings.application.SettingsQueryResponseMother;
import dev.team.readtoday.server.settings.application.findforuser.FindUserSettingsQuery;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SettingsGetControllerTest {

  @Test
  void shouldReturnOkStatusIfSucceeded() {
    // Given
    QueryBus queryBus = mock(QueryBus.class);
    SettingsGetController controller = new SettingsGetController(queryBus);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    String userId = UserId.random().toString();
    when(securityContext.getUserPrincipal()).thenReturn(() -> userId);

    var queryCaptor = ArgumentCaptor.forClass(FindUserSettingsQuery.class);
    var queryResponse = SettingsQueryResponseMother.withUserId(userId);
    when(queryBus.ask(queryCaptor.capture())).thenReturn(queryResponse);

    // When
    Response response = controller.getSettings();

    // Then
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldReturnReceivedSettingsIfSucceeded() {
    // Given
    QueryBus queryBus = mock(QueryBus.class);
    SettingsGetController controller = new SettingsGetController(queryBus);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    String userId = UserId.random().toString();
    when(securityContext.getUserPrincipal()).thenReturn(() -> userId);

    var queryCaptor = ArgumentCaptor.forClass(FindUserSettingsQuery.class);
    var queryResponse = SettingsQueryResponseMother.withUserId(userId);
    when(queryBus.ask(queryCaptor.capture())).thenReturn(queryResponse);

    // When
    Response response = controller.getSettings();

    // Then
    SettingsResponse entity = (SettingsResponse) response.getEntity();
    assertEquals(userId, queryResponse.getUserId());
    assertEquals(queryResponse.getZoneId(), entity.getZoneId());
    assertEquals(queryResponse.getZoneId(), entity.getZoneId());
  }
}
