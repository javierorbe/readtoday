package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.SearchCategory;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.channel.application.SearchChannelByCategory;
import dev.team.readtoday.server.channel.domain.Channel;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("channels")
public final class ChannelSearchController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelSearchController.class);

  @Inject
  private SearchChannelByCategory searchChannelByCategory;

  @Inject
  private SearchCategory searchCategory;

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ChannelsByCategoryResponse getAllByCategoryName(
      @QueryParam("categoryName") String categoryName) {

    LOGGER.trace("Received get channels by category name request: " + categoryName);

    try {
      List<Channel> channels = searchChannelByCategory.getAllByCategoryName(categoryName);
      Set<Category> categories = searchCategory.getFromChannels(channels);
      ChannelsByCategoryResponse response = ChannelsByCategoryResponse.create(channels, categories);

      LOGGER.debug("Successful channels by category name request");
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.debug("Error getting channel by category name.");
      return null;
    }
  }
}
