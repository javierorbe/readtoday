package dev.team.readtoday.client.create;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

public final class ChannelCreationRequest {

  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private ImmutableList<String> categoryIds;

  public ChannelCreationRequest(String title,
                                String rssUrl,
                                String description,
                                String imageUrl,
                                Collection<String> categoryIds) {
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categoryIds = ImmutableList.copyOf(categoryIds);
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
}
