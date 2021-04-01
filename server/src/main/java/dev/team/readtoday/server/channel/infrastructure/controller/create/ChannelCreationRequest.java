package dev.team.readtoday.server.channel.infrastructure.controller.create;

import java.util.List;

public class ChannelCreationRequest {

  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private List<String> categoryIds;

  public ChannelCreationRequest(String title, String rssUrl, String description,
      String imageUrl, List<String> categoryIds) {
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categoryIds = categoryIds;
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
}
