package dev.team.readtoday.server.customlist.infrastructure.controller.get;

import dev.team.readtoday.server.customlist.application.SearchCustomList;
import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.publication.application.get.GetPublication;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("custom-list/get")
public class CustomListGetPublicationsController extends BaseController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomListGetPublicationsController.class);

  private final SearchCustomList searchCustomList;
  private final GetPublication getPublication;


  @Autowired
  public CustomListGetPublicationsController(SearchCustomList searchCustomList,
      GetPublication getPublication) {
    this.searchCustomList = searchCustomList;
    this.getPublication = getPublication;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getPublicationsOfCustomList(CustomListGetPublicationsRequest request) {
    LOGGER.trace("Received publication get request for custom list {}", request.getCustomListId());

    try {

      Optional<CustomList> result =
          searchCustomList.search(CustomListId.fromString(request.getCustomListId()));

      if (result.isEmpty()) {
        LOGGER.trace("Custom list not found");
        return response(Status.NOT_FOUND);
      }

      Collection<PublicationId> publicationIds = result.get().getPublications();
      Collection<Publication> publications = new ArrayList<>();
      for (PublicationId publicationId : publicationIds) {
        Optional<Publication> optPublication = getPublication.get(publicationId);
        optPublication.ifPresent(publications::add);
      }

      CustomListGetPublicationsResponse response =
          new CustomListGetPublicationsResponse(publications);
      return Response.ok(response).build();

    } catch (Exception e) {
      LOGGER.trace("Server error");
      return response(Status.BAD_REQUEST);
    }
  }
}
