package dev.team.readtoday.server.subscription.infrastructure.controller.unsubscribe;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.subscription.application.DeleteSubscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionNotFound;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("unsubscribe")
public final class UnsubscribeController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnsubscribeController.class);

  private final DeleteSubscription deleteSubscription;

  @Autowired
  public UnsubscribeController(DeleteSubscription deleteSubscription) {
    this.deleteSubscription = deleteSubscription;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response unsubscribe(@QueryParam("channelId") String channelIdStr) {
    UserId userId = getRequestUserId();
    LOGGER.debug("Received unsubscription request (userId: {}, channelId: {}).", userId,
        channelIdStr);

    try {
      ChannelId channelId = ChannelId.fromString(channelIdStr);
      deleteSubscription.delete(userId, channelId);
      LOGGER.trace("Successful unsubscription request.");
      return response(Status.NO_CONTENT);
    } catch (SubscriptionNotFound e) {
      return response(Status.NO_CONTENT);
    } catch (RuntimeException e) {
      LOGGER.debug("Error deleting subscription.", e);
      return response(Status.BAD_REQUEST);
    }
  }
}
