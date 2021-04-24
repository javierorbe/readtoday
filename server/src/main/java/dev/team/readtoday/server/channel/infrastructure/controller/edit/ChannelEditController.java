package dev.team.readtoday.server.channel.infrastructure.controller.edit;

import dev.team.readtoday.server.channel.application.edit.EditChannelCommand;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("channels")
public class ChannelEditController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEditController.class);

  private final CommandBus commandBus;
  private final SearchUserById searchUserById;

  @Autowired
  public ChannelEditController(CommandBus commandBus, SearchUserById searchUserById) {
    this.commandBus = commandBus;
    this.searchUserById = searchUserById;
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response editChannel(ChannelEditRequest request, @PathParam("id") String id) {
    LOGGER.trace("Received channel edit request: {}", request);

    User user = searchUserById.search(getRequestUserId());

    if (!user.isAdmin()) {
      return response(Status.FORBIDDEN);
    }

    Command editChannelCommand = createEditCommand(id, request);

    commandBus.dispatch(editChannelCommand);

    return Response.ok().build();
  }

  private static Command createEditCommand(String id, ChannelEditRequest request) {
    return new EditChannelCommand(
        id,
        request.getTitle(),
        request.getRssUrl(),
        request.getDescription(),
        request.getImageUrl(),
        request.getCategoryIds()
    );
  }
}
