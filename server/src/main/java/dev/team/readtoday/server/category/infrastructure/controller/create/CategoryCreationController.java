package dev.team.readtoday.server.category.infrastructure.controller.create;

import dev.team.readtoday.server.category.application.CreateCategory;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.RequiresAuth;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("categories")
public class CategoryCreationController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCreationController.class);

  private final CreateCategory createCategory;
  private final SearchUserById searchUserById;

  @Context
  private UriInfo uriInfo;

  @Autowired
  public CategoryCreationController(CreateCategory createCategory, SearchUserById searchUserById) {
    this.createCategory = createCategory;
    this.searchUserById = searchUserById;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createCategory(CategoryCreationRequest request) {
    LOGGER.trace("Received category creation request: {}", request);

    User user = searchUserById.search(getRequestUserId());

    if (!user.isAdmin()) {
      return response(Status.FORBIDDEN);
    }

    try {
      CategoryName categoryName = new CategoryName(request.getName());
      Category category = createCategory.create(categoryName);
      URI location = buildResourceLocation(category);
      LOGGER.debug("Successful category creation request: {}", category);
      return Response.created(location).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Error creating the category.", e);
      return response(Status.BAD_REQUEST);
    }
  }

  private URI buildResourceLocation(Category category) {
    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
    uriBuilder.path(category.getId().toString());
    return uriBuilder.build();
  }
}
