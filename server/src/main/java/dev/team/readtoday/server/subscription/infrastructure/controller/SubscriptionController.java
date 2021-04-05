package dev.team.readtoday.server.subscription.infrastructure.controller;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.shared.infrastructure.controller.RequiresAuth;
import dev.team.readtoday.server.subscription.application.CreateSubscription;
import dev.team.readtoday.server.user.application.SearchUserById;
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
import java.security.Principal;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresAuth
@Path("subscriptions")
public final class SubscriptionController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

  @Inject
  private CreateSubscription createSubscription;
  @Inject
  private SearchUserById searchUserById;

  @Context
  private SecurityContext securityContext;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createSubscription(SubscriptionRequest request) {
    LOGGER.trace("Received subscription request");

    UserId userId = getUserId();
    Optional<User> optUser = searchUserById.search(userId);

    if (optUser.isEmpty()) {
      LOGGER.debug("Unauthorized subscription");
      return Response.status(Status.UNAUTHORIZED).build();
    }

    try {
      ChannelId channelId = ChannelId.fromString(request.getChannelId());
      createSubscription.create(userId, channelId);

      LOGGER.trace("Successful subscription request");
      return Response.status(Status.CREATED).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Error creating subscription.", e);
      return Response.status(Status.BAD_REQUEST).build();
    }
  }

  private UserId getUserId() {
    Principal principal = securityContext.getUserPrincipal();
    String userId = principal.getName();
    return UserId.fromString(userId);
  }
}
