package dev.team.readtoday.server.channel.infrastructure.controller.create;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

public final class TestChannelCreationRequest {

  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private ImmutableList<String> categoryIds;

  public TestChannelCreationRequest(String title,
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
