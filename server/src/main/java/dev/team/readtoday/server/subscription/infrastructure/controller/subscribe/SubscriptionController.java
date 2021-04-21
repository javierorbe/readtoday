package dev.team.readtoday.server.subscription.infrastructure.controller.subscribe;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.subscription.application.CreateSubscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionAlreadyExists;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("subscribe")
public final class SubscriptionController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

  private final CreateSubscription createSubscription;

  @Autowired
  public SubscriptionController(CreateSubscription createSubscription) {
    this.createSubscription = createSubscription;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response subscribe(@QueryParam("channelId") String channelIdStr) {
    UserId userId = getRequestUserId();
    LOGGER.debug("Received unsubscription request (userId: {}, channelId: {}).",
        userId, channelIdStr);

    try {
      ChannelId channelId = ChannelId.fromString(channelIdStr);
      createSubscription.create(userId, channelId);
      LOGGER.trace("Successful subscription request.");
      return response(Status.CREATED);
    } catch (SubscriptionAlreadyExists e) {
      return response(Status.CONFLICT, "Already subscribed.");
    } catch (RuntimeException e) {
      LOGGER.debug("Error creating subscription.", e);
      return response(Status.BAD_REQUEST);
    }
  }
}
