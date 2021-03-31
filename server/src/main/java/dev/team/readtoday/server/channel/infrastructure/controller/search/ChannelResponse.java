package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.channel.domain.Channel;
import java.util.ArrayList;
import java.util.List;

public class ChannelResponse {
  private String id;
  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private List<String> categoryIds;

  public ChannelResponse(String title, String rssUrl, String description,
      String imageUrl, List<String> categoryIds) {
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categoryIds = categoryIds;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getRssUrl() {
    return rssUrl;
  }

  public void setRssUrl(String rssUrl) {
    this.rssUrl = rssUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<String> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<String> categoryIds) {
    this.categoryIds = categoryIds;
  }

  private static ChannelResponse fromChannel(Channel channel) {

    List<String> categoriesIds = new ArrayList<>();
    channel.getCategoryIds().forEach(categoriesId -> categoriesIds.add(categoriesId.toString()));

    return new ChannelResponse(
        channel.getTitle().toString(),
        channel.getRssUrl().toString(),
        channel.getDescription().toString(),
        channel.getImageUrl().toString(),
        categoriesIds
    );
  }

  public static List<ChannelResponse> fromChannels(List<Channel> channels) {
    List<ChannelResponse> response = new ArrayList<>();

    channels.forEach(channel -> response.add(ChannelResponse.fromChannel(channel)));

    return response;
  }
}
