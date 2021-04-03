package dev.team.readtoday.client.search;

import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ChannelsByCategoryResponse {

  private List<ChannelResponse> channels;
  private List<CategoryResponse> categories;

  public List<ChannelResponse> getChannels() {
    return channels;
  }

  public List<CategoryResponse> getCategories() {
    return categories;
  }

  public void setChannels(
      List<ChannelResponse> channels) {
    this.channels = channels;
  }

  public void setCategories(List<CategoryResponse> categories) {
    this.categories = categories;
  }

  public List<Channel> toChannels() {
    List<Channel> channels = new ArrayList<>();
    Map<UUID, Category> categories = CategoryResponse.toCategories(this.categories);

    this.channels
        .forEach(channelResponse -> channels.add(channelResponse.toChannel(categories)));

    return channels;
  }
}
