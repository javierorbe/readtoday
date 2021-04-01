package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.SearchCategory;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.infrastructure.controller.CategoryResponse;
import dev.team.readtoday.server.channel.application.SearchChannelByCategory;
import dev.team.readtoday.server.channel.domain.Channel;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChannelSearchController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelSearchController.class);

  @Inject
  private SearchChannelByCategory searchChannelByCategory;

  @Inject
  private SearchCategory searchCategory;

  @GET
  @Consumes
  public ChannelsByCategoryResponse getAllByCategoryId(
      @QueryParam("categoryId") String categoryId) {

    LOGGER.trace("Received get channels by category id request");

    try {
      List<Channel> channels = searchChannelByCategory.getAllByCategoryId(categoryId);
      Set<Category> categories = searchCategory.getFromChannels(channels);
      ChannelsByCategoryResponse response = ChannelsByCategoryResponse.create(channels, categories);

      LOGGER.debug("Successful channels by category id request");
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.debug("Error getting channel by category id.");
      return null;
    }
  }

  @GET
  @Consumes
  public ChannelsByCategoryResponse getAllByCategoryName(
      @QueryParam("categoryName") String categoryName) {

    LOGGER.trace("Received get channels by category name request");

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