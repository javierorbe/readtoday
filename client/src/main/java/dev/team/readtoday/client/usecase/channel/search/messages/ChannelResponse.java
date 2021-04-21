package dev.team.readtoday.client.usecase.channel.search.messages;

import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ChannelResponse {

  private String id;
  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private List<String> categoryIds;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getRssUrl() {
    return rssUrl;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public List<String> getCategoryIds() {
    return categoryIds;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setRssUrl(String rssUrl) {
    this.rssUrl = rssUrl;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setCategoryIds(List<String> categoryIds) {
    this.categoryIds = categoryIds;
  }

  public Channel toModel(Map<String, Category> categoryMap) {
    Collection<Category> categories =
        categoryIds.stream().map(categoryMap::get).collect(Collectors.toSet());
    return new Channel(id, title, rssUrl, description, imageUrl, categories);
  }
}
