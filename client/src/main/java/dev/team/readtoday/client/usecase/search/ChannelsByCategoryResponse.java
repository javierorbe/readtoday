package dev.team.readtoday.client.usecase.search;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import java.util.List;
import java.util.Map;

public final class ChannelsByCategoryResponse {

  private List<ChannelResponse> channels;
  private List<CategoryResponse> categories;

  public void setChannels(List<ChannelResponse> channels) {
    this.channels = channels;
  }

  public void setCategories(List<CategoryResponse> categories) {
    this.categories = categories;
  }

  ImmutableCollection<Channel> toChannelCollection() {
    Map<String, Category> categoryMap = CategoryResponse.buildCategoryMap(categories);
    return channels.stream()
        .map(channel -> channel.toModel(categoryMap))
        .collect(ImmutableSet.toImmutableSet());
  }
}
