package dev.team.readtoday.server.customlist.infrastructure.controller.create;

import dev.team.readtoday.server.customlist.application.CreateCustomListCommand;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("custom-list")
public class CustomListCreationController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomListCreationController.class);

  private final CommandBus commandBus;
  private final SearchUserById searchUserById;

  @Autowired
  public CustomListCreationController(CommandBus commandBus, SearchUserById searchUserById) {
    this.commandBus = commandBus;
    this.searchUserById = searchUserById;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createCustomList(CustomListCreationRequest request) {
    LOGGER.trace("Received custom list creation request {}", request);

    User user = searchUserById.search(getRequestUserId());

    Command command = createCustomListCommand(user.getId(), request);

    commandBus.dispatch(command);

    LOGGER.trace("Custom list was created successfully");

    return Response.ok().build();
  }

  private static Command createCustomListCommand(UserId userId, CustomListCreationRequest request) {
    return new CreateCustomListCommand(request.getTitle(), userId.toString());
  }
}
