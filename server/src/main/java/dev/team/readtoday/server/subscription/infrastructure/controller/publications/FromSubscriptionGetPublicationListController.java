package dev.team.readtoday.server.subscription.infrastructure.controller.publications;

import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.publication.application.search.PublicationResponse;
import dev.team.readtoday.server.publication.application.search.SearchPublicationsQuery;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.subscription.application.GetUserSubscriptions;
import dev.team.readtoday.server.subscription.domain.Subscription;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("/subscriptions/publications")
public final class FromSubscriptionGetPublicationListController extends BaseController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(FromSubscriptionGetPublicationListController.class);

  private final GetUserSubscriptions getSubscriptions;
  private final QueryBus queryBus;

  @Autowired
  public FromSubscriptionGetPublicationListController(GetUserSubscriptions getSubscriptions,
      QueryBus queryBus) {
    this.getSubscriptions = getSubscriptions;
    this.queryBus = queryBus;
  }

  @GET
  public Response publicationList() {
    LOGGER.trace("Publications from subscriptions request.");
    Collection<Subscription> subscriptions = getSubscriptions.search(getRequestUserId());
    Collection<PublicationResponse> publications = new ArrayList<PublicationResponse>();

    for (Subscription subscription : subscriptions) {
      var response = queryBus.ask(new SearchPublicationsQuery(
          Collections.singleton(subscription.getChannelId().toString())));
      publications.addAll(response.getPublications());
    }

    if (publications.isEmpty()) {
      throw new ChannelNotFound();
    }
    PublicationListResponse publicationList = new PublicationListResponse(publications);
    return Response.ok(publicationList).build();
  }

}
