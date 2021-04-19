package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.publication.application.search.PublicationResponse;
import dev.team.readtoday.server.publication.application.search.SearchPublicationsQuery;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("publications")
public final class ChannelPublicationListGetController extends BaseController {

  private final QueryBus queryBus;

  @Autowired
  public ChannelPublicationListGetController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @GET
  public Response getChannelPublicationList(@QueryParam("channelId") String channelId) {
    var response = queryBus.ask(new SearchPublicationsQuery(Collections.singleton(channelId)));
    Collection<PublicationResponse> publications = response.getPublications();

    if (publications.isEmpty()) {
      throw new ChannelNotFound();
    }

    return Response.ok(publications).build();
  }
}
