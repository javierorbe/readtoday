package dev.team.readtoday.server.subscription.infrastructure.controller.get;


import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.subscription.application.GetUserSubscriptions;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionNotFound;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


@Path("subscriptions")
public final class SubscriptionGetController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionGetController.class);

  private final GetUserSubscriptions getUserSubscriptions;
  @Autowired
  public SubscriptionGetController(GetUserSubscriptions getUserSubscriptions) {
       this.getUserSubscriptions = getUserSubscriptions;

  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getSubscription() {

    try{
        Collection<Subscription> subscriptions = getUserSubscriptions.search(getRequestUserId());
        LOGGER.trace("Successful getting subscriptions request.");
        return Response.ok(subscriptions).build();
    }catch(SubscriptionNotFound e){
          LOGGER.debug("Error getting subscriptions.", e);
          return response(Response.Status.BAD_REQUEST);
    }
  }



}
