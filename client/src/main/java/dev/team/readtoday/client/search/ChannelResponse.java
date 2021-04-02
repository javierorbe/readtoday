package dev.team.readtoday.client.search;

import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ChannelResponse {
  private String id;
  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private List<String> categoryIds;

  public ChannelResponse() {
    categoryIds = List.of();
  }

  public ChannelResponse(String id, String title, String rssUrl, String description,
      String imageUrl, List<String> categoryIds) {
    this.id = id;
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


  public Channel toChannel(Map<UUID, Category> categories) {

    Collection<Category> categoriesOfChannel = new HashSet<>();

    categoryIds.forEach(id -> {
      Category category = categories.get(UUID.fromString(id));
      categoriesOfChannel.add(category);
    });


    return new Channel(
        UUID.fromString(id),
        title,
        imageUrl,
        categoriesOfChannel
    );
  }
}
