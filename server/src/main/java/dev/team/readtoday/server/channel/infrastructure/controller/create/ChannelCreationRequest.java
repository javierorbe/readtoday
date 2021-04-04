package dev.team.readtoday.server.channel.infrastructure.controller.create;

import java.util.List;

public final class ChannelCreationRequest {

  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private List<String> categoryIds;

  String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  String getRssUrl() {
    return rssUrl;
  }

  public void setRssUrl(String rssUrl) {
    this.rssUrl = rssUrl;
  }

  String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  List<String> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<String> categoryIds) {
    this.categoryIds = categoryIds;
  }
}
