package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.infrastructure.controller.CategoryResponse;
import dev.team.readtoday.server.channel.domain.Channel;
import java.util.List;
import java.util.Set;

public final class ChannelsByCategoryResponse {

  private final List<ChannelResponse> channelsResponse;
  private final List<CategoryResponse> categoriesResponse;

  public ChannelsByCategoryResponse(
      List<ChannelResponse> channelsResponse,
      List<CategoryResponse> categoriesResponse) {
    this.channelsResponse = channelsResponse;
    this.categoriesResponse = categoriesResponse;
  }

  public List<ChannelResponse> getChannelsResponse() {
    return channelsResponse;
  }

  public List<CategoryResponse> getCategoriesResponse() {
    return categoriesResponse;
  }

  public static ChannelsByCategoryResponse create(List<Channel> channels,
      Set<Category> categories) {
    List<ChannelResponse> channelResponses = ChannelResponse.fromChannels(channels);
    List<CategoryResponse> categoryResponses = CategoryResponse.fromCategories(categories);

    return new ChannelsByCategoryResponse(channelResponses, categoryResponses);
  }
}
