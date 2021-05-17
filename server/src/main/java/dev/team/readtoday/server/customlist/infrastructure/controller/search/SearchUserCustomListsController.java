package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import dev.team.readtoday.server.customlist.application.SearchListsFromUser;
import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RequiresAuth
@Path("custom-list/search")
public class SearchUserCustomListsController extends BaseController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SearchUserCustomListsController.class);

  private final SearchListsFromUser searchListsFromUser;

  @Autowired
  public SearchUserCustomListsController(SearchListsFromUser searchListsFromUser) {
    this.searchListsFromUser = searchListsFromUser;
  }

  @GET
  public Response searchUserCustomLists() {
    LOGGER.trace("Search user's custom lists request received.");

    Collection<CustomList> customLists = searchListsFromUser.search(getRequestUserId());
    if (customLists.isEmpty()) {
      LOGGER.trace("There are no custom lists for this user");
      return response(Status.NOT_FOUND);
    }

    Collection<CustomListResponse> customListResponses = new ArrayList<>();
    for (CustomList customList : customLists) {
      Collection<String> publicationsId = new ArrayList<>();
      if (!customList.getPublications().isEmpty()) {
        for (PublicationId id : customList.getPublications()) {
          publicationsId.add(id.toString());
        }
      }
      CustomListResponse custom = new CustomListResponse(customList.getId().toString(),
          customList.getTitle().toString(), customList.getUserId().toString(), publicationsId);
      customListResponses.add(custom);
    }

    SearchUserCustomListsResponse response = new SearchUserCustomListsResponse(customListResponses);
    return Response.ok(response).build();
  }
}
