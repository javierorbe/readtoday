package dev.team.readtoday.server.user.infrastructure.controller.signup;

import dev.team.readtoday.server.jwt.application.get.GetJwtTokenQuery;
import dev.team.readtoday.server.jwt.application.get.GetJwtTokenQueryResponse;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.user.application.signup.SignUpUserCommand;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/auth/signup")
public final class SignUpController extends BaseController {

  private final QueryBus queryBus;
  private final CommandBus commandBus;

  @Autowired
  public SignUpController(CommandBus commandBus, QueryBus queryBus) {
    this.queryBus = queryBus;
    this.commandBus = commandBus;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signUp(SignUpRequest request) {
    String userId = UserId.random().toString();
    commandBus.dispatch(
        new SignUpUserCommand(userId, request.getUsername(), request.getAccessToken())
    );

    GetJwtTokenQueryResponse resp = queryBus.ask(new GetJwtTokenQuery(userId));
    String jwtToken = resp.getJwtToken();

    return Response.status(Status.CREATED).entity(jwtToken).build();
  }
}
