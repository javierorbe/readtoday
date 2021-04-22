package dev.team.readtoday.server.readlater.infrastructure.persistence.controller.get;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.readlater.application.SearchReadLaterList;
import dev.team.readtoday.server.readlater.domain.ReadLaterListNotFound;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.user.domain.NonExistingUser;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
//@Path("subscriptions")
public final class ReadLaterListController extends BaseController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReadLaterListController.class);

  private final SearchReadLaterList searchPublications;

  @Autowired
  public ReadLaterListController(SearchReadLaterList searchPublications){
    this.searchPublications = searchPublications;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getReadLaterLis(){
    LOGGER.trace("Received readLater list request.");

    try{
      Collection<Publication> publications = (Collection<Publication>) searchPublications.search(getRequestUserId());
      ReadLaterListResponse response = new ReadLaterListResponse(publications);
      return Response.ok(response).build();
    }catch(ReadLaterListNotFound | NonExistingUser e){
      LOGGER.debug("Error getting readLater list.", e);
      return response(Response.Status.BAD_REQUEST);
    }
  }
}
