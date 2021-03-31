package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.SearchCategory;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.infrastructure.controller.CategoryResponse;
import dev.team.readtoday.server.channel.application.SearchChannelByCategory;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.infrastructure.controller.create.ChannelCreateController;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelSearchController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCreateController.class);

  @Inject
  private SearchChannelByCategory searchChannelByCategory;

  @Inject
  private SearchCategory searchCategory;

  @GET
  @Consumes
  public ChannelsByCategoryResponse getAllByCategory(@QueryParam("categoryId") String categoryId) {

    LOGGER.trace("Received get channels by category request");

    try {
      List<Channel> channels = searchChannelByCategory.getAllByCategoryId(categoryId);
      Set<Category> categories = searchCategory.getFromChannels(channels);

      List<ChannelResponse> channelResponses = ChannelResponse.fromChannels(channels);
      List<CategoryResponse> categoryResponses = CategoryResponse.fromCategories(categories);

      ChannelsByCategoryResponse response =
          new ChannelsByCategoryResponse(channelResponses, categoryResponses);

      LOGGER.debug("Successful channels by category request");
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.debug("Error getting channel by category.");
      return null;
    }
  }

}
