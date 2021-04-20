package dev.team.readtoday.server.settings.infrastructure.controller.get;

import dev.team.readtoday.server.settings.application.findforuser.FindUserSettingsQuery;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("/settings")
public final class SettingsGetController extends BaseController {

  private final QueryBus queryBus;

  @Autowired
  public SettingsGetController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSettings() {
    String userId = getRequestUserId().toString();
    var settings = queryBus.ask(new FindUserSettingsQuery(userId));
    var responseEntity = SettingsResponse.fromQueryResponse(settings);
    return Response.ok(responseEntity).build();
  }
}
