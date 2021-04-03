package dev.team.readtoday.server.channel.infrastructure.controller.create;

import dev.team.readtoday.server.channel.application.CreateChannel;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.infrastructure.controller.RequiresAuth;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserId;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresAuth
@Path("channels")
public final class ChannelCreationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCreationController.class);

  @Inject
  private CreateChannel createChannel;
  @Inject
  private SearchUserById searchUserById;

  @Context
  private SecurityContext securityContext;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createChannel(ChannelCreationRequest newChannel, @Context UriInfo uriInfo) {

    LOGGER.trace("Received channel creation request: {}", newChannel);
    Optional<User> optRequester = searchUserById.search(getRequestUserId());

    // If not user found
    if (optRequester.isEmpty()) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    User requester = optRequester.get();

    // If not admin
    if (Role.ADMIN != requester.getRole()) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    // If admin, then create channel
    try {
      Channel channel = newChannel.toChannel();
      createChannel.createChannel(channel);
      UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
      uriBuilder.path(channel.getId().toString());

      LOGGER.debug("Successful channel creation request request");
      return Response.created(uriBuilder.build()).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Error creating the channel.", e);
      return Response.status(Status.BAD_REQUEST).build();
    }
  }

  /**
   * Get requester user id
   *
   * @return Requester id
   */
  private UserId getRequestUserId() {
    Principal principal = securityContext.getUserPrincipal();

    String userId = principal.getName();

    return UserId.fromString(userId);
  }
}
