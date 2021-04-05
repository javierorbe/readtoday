package dev.team.readtoday.server.subscription.infrastructure.controller;

import java.security.Principal;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.shared.infrastructure.controller.RequiresAuth;
import dev.team.readtoday.server.subscription.application.DeleteSubscription;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserId;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@RequiresAuth
@Path("subscriptions")
public final class UnsubscribeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnsubscribeController.class);

  @Inject
  private DeleteSubscription deleteSubscription;
  @Inject
  private SearchUserById searchUserById;

  @Context
  private SecurityContext securityContext;

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createSubscription(@QueryParam("channelId") String channelId) {
    LOGGER.trace("Received subscription request");

    UserId userId = getUserId();
    Optional<User> optUser = searchUserById.search(userId);

    if (optUser.isEmpty()) {
      LOGGER.debug("Unauthorized subscription");
      return Response.status(Status.UNAUTHORIZED).build();
    }

    try {

      deleteSubscription.delete(userId, ChannelId.fromString(channelId));

      LOGGER.trace("Successful unsubscription request");
      return Response.status(Status.NO_CONTENT).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Error deleting subscription.", e);
      return Response.status(Status.BAD_REQUEST).build();
    }
  }

  private UserId getUserId() {
    Principal principal = securityContext.getUserPrincipal();
    String userId = principal.getName();
    return UserId.fromString(userId);
  }
}
