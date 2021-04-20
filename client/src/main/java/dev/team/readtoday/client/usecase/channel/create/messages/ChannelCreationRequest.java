package dev.team.readtoday.client.usecase.channel.create.messages;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

public class ChannelCreationRequest {

  private final String title;
  private final String rssUrl;
  private final String description;
  private final String imageUrl;
  private final ImmutableList<String> categoryIds;

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
