package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.infrastructure.controller.CategoryResponse;
import java.util.List;

public class ChannelsByCategoryResponse {

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
}
