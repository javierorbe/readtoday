package dev.team.readtoday.server.category.infrastructure.controller.search;


import dev.team.readtoday.server.category.application.SearchAllCategories;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("categories")
public class SearchAllCategoriesController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchAllCategoriesController.class);

  private final SearchAllCategories searchCategories;

  @Autowired
  public SearchAllCategoriesController(
      SearchAllCategories searchCategories) {
    this.searchCategories = searchCategories;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllCategories() {
    LOGGER.trace("Received get all categories request");

    try {
      Collection<Category> categories = searchCategories.getAll();
      LOGGER.debug("Successful getting all categories request");
      AllCategoriesResponse response = new AllCategoriesResponse(categories);

      return Response.ok(response).build();
    } catch (RuntimeException e) {
      LOGGER.trace("Getting all categories request failed");
      return response(Status.NOT_FOUND);
    }
  }
}
