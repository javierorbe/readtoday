package dev.team.readtoday.client.search;

import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ChannelsByCategoryResponse {

  private List<ChannelResponse> channelsResponse;
  private List<CategoryResponse> categoriesResponse;

  public ChannelsByCategoryResponse() {
    channelsResponse = List.of();
    categoriesResponse = List.of();
  }

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

  public void setChannelsResponse(
      List<ChannelResponse> channelsResponse) {
    this.channelsResponse = channelsResponse;
  }

  public void setCategoriesResponse(
      List<CategoryResponse> categoriesResponse) {
    this.categoriesResponse = categoriesResponse;
  }


  public List<Channel> toChannels() {
    List<Channel> channels = new ArrayList<>();
    Map<UUID, Category> categories = CategoryResponse.toCategories(categoriesResponse);

    channelsResponse
        .forEach(channelResponse -> channels.add(channelResponse.toChannel(categories)));

    return channels;
  }
}
