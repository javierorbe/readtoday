package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.infrastructure.controller.CategoryResponse;
import dev.team.readtoday.server.channel.domain.Channel;
import java.util.List;
import java.util.Set;

public final class ChannelsByCategoryResponse {

  private final List<ChannelResponse> channelResponse;
  private final List<CategoryResponse> categories;

  public ChannelsByCategoryResponse(
      List<ChannelResponse> channelResponse,
      List<CategoryResponse> categories) {
    this.channelResponse = channelResponse;
    this.categories = categories;
  }

  public List<ChannelResponse> getChannelResponse() {
    return channelResponse;
  }

  public List<CategoryResponse> getCategories() {
    return categories;
  }

  public static ChannelsByCategoryResponse create(List<Channel> channels,
      Set<Category> categories) {
    List<ChannelResponse> channelResponses = ChannelResponse.fromChannels(channels);
    List<CategoryResponse> categoryResponses = CategoryResponse.fromCategories(categories);

    return new ChannelsByCategoryResponse(channelResponses, categoryResponses);
  }
}
