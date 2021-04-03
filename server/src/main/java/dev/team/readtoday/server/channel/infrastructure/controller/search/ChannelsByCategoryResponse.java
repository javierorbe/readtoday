package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.channel.domain.Channel;
import java.util.Collection;
import java.util.List;

public final class ChannelsByCategoryResponse {

  private final List<ChannelResponse> channels;
  private final List<CategoryResponse> categories;

  ChannelsByCategoryResponse(Collection<Channel> channels,
                                     Collection<Category> categories) {
    this.channels = ChannelResponse.fromChannels(channels);
    this.categories = CategoryResponse.fromCategories(categories);
  }

  public Collection<ChannelResponse> getChannels() {
    return channels;
  }

  public Collection<CategoryResponse> getCategories() {
    return categories;
  }
}
