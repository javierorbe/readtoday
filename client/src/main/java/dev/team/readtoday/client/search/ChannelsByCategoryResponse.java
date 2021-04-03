package dev.team.readtoday.client.search;

import java.util.List;

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
}
