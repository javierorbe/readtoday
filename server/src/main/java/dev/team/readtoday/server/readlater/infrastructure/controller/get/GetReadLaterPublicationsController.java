package dev.team.readtoday.server.readlater.infrastructure.controller.get;

import dev.team.readtoday.server.category.application.search.CategoryResponse;
import dev.team.readtoday.server.publication.application.get.GetPublication;
import dev.team.readtoday.server.publication.application.search.PublicationResponse;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.readlater.application.SearchReadLaterList;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.user.domain.NonExistingUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("readlater")
public class GetReadLaterPublicationsController extends BaseController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(GetReadLaterPublicationsController.class);

  private final SearchReadLaterList searchReadLaterList;
  private final GetPublication getPublication;

  @Autowired
  public GetReadLaterPublicationsController(SearchReadLaterList searchReadLaterList,
      GetPublication getPublication) {
    this.searchReadLaterList = searchReadLaterList;
    this.getPublication = getPublication;
  }

  @GET
  public Response getPublications() {
    LOGGER.trace("Recived readlater publication list request.");
    try {
      // Gets publications IDs from user's read later
      Collection<PublicationId> publicationIds =
          searchReadLaterList.search(getRequestUserId()).getPublicationlist();

      // If there aren't any
      if (publicationIds.isEmpty()) {
        LOGGER.trace("There are no publications in read later");
        return response(Status.NOT_FOUND);
      }

      // Gets publications from the IDs
      Collection<PublicationResponse> publications = new ArrayList<>();
      Collection<CategoryResponse> categories = new ArrayList<>();
      for (PublicationId pubId : publicationIds) {
        Optional<Publication> publication = getPublication.get(pubId);
        if (publication.isPresent()) {
          publications.add(new PublicationResponse(publication.get(), categories));
        }
      }
      GetReadLaterPublicationsResponse publicationList =
          new GetReadLaterPublicationsResponse(publications);
      return Response.ok(publicationList).build();
    } catch (NonExistingUser e) {
      LOGGER.error("User not found.");
      return response(Status.UNAUTHORIZED);
    }
  }
}
