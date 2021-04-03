package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.SearchCategoriesById;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.application.SearchChannelsByCategory;
import dev.team.readtoday.server.channel.domain.CategoryDoesNotExist;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.controller.RequiresAuth;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresAuth
@Path("channels")
public final class ChannelSearchController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelSearchController.class);

  @Inject
  private SearchChannelsByCategory searchChannelsByCategory;

  @Inject
  private SearchCategoriesById searchCategoriesById;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllByCategoryName(@QueryParam("categoryName") CategoryName categoryName) {

    LOGGER.trace("Received get channels by category name request: {}", categoryName);

    try {
      Collection<Channel> channels = searchChannelsByCategory.search(categoryName);
      Collection<CategoryId> categoryIds = flatChannelCategories(channels);
      Collection<Category> categories = searchCategoriesById.search(categoryIds);
      ChannelsByCategoryResponse response = new ChannelsByCategoryResponse(channels, categories);

      LOGGER.debug("Successful channels by category name request");
      return Response.ok(response).build();
    } catch (CategoryDoesNotExist e) {
      LOGGER.trace("Channel search by category request failed.", e);
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (RuntimeException e) {
      LOGGER.debug("Error getting channels by category name.", e);
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  private static Collection<CategoryId> flatChannelCategories(Collection<Channel> channels) {
    return channels.stream()
        .flatMap(channel -> channel.getCategories().stream())
        .collect(Collectors.toSet());
  }
}
