package dev.team.readtoday.server.settings.infrastructure.controller.put;

import dev.team.readtoday.server.settings.application.update.UpdateSettingsCommand;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("/settings")
public final class SettingsPutController extends BaseController {

  private final CommandBus commandBus;

  @Autowired
  public SettingsPutController(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateSettings(SettingsRequest request) {
    commandBus.dispatch(new UpdateSettingsCommand(
        getRequestUserId().toString(),
        request.getZoneId(),
        request.getNotificationPref()
    ));
    return response(Status.OK);
  }
}
