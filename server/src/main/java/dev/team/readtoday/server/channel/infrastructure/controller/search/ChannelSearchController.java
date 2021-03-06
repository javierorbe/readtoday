package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.search.SearchCategory;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.application.SearchChannelsByCategory;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
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
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("channels")
public final class ChannelSearchController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelSearchController.class);

  private final SearchChannelsByCategory searchChannels;
  private final SearchCategory searchCategoriesById;

  @Autowired
  public ChannelSearchController(SearchChannelsByCategory searchChannels,
                                 SearchCategory searchCategoriesById) {
    this.searchChannels = searchChannels;
    this.searchCategoriesById = searchCategoriesById;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllByCategoryName(@QueryParam("categoryName") CategoryName categoryName) {
    LOGGER.trace("Received get channels by category name request: {}", categoryName);

    Collection<Channel> channels = searchChannels.search(categoryName);
    Collection<CategoryId> categoryIds = flatChannelCategories(channels);
    Collection<Category> categories = searchCategoriesById.apply(categoryIds);
    ChannelsByCategoryResponse response = new ChannelsByCategoryResponse(channels, categories);

    LOGGER.debug("Successful channels by category name request");
    return Response.ok(response).build();
  }

  private static Collection<CategoryId> flatChannelCategories(Collection<Channel> channels) {
    return channels.stream()
        .flatMap(channel -> channel.getCategories().stream())
        .collect(Collectors.toSet());
  }
}
