package dev.team.readtoday.server.channel.application.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.Identifier;
import java.util.Collection;
import java.util.Collections;

public final class ChannelResponse {

  private final String id;
  private final String title;
  private final String rssUrl;
  private final String description;
  private final String imageUrl;
  private final Collection<String> categoryIds;

  private ChannelResponse(String id,
                          String title,
                          String rssUrl,
                          String description,
                          String imageUrl,
                          Collection<String> categoryIds) {
    this.id = id;
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categoryIds = Collections.unmodifiableCollection(categoryIds);
  }

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

  public Collection<String> getCategoryIds() {
    return categoryIds;
  }

  static ChannelResponse fromDomainObject(Channel channel) {
    Collection<String> categoryIds =
        channel.getCategories().stream().map(Identifier::toString).toList();
    return new ChannelResponse(
        channel.getId().toString(),
        channel.getTitle().toString(),
        channel.getRssUrl().toString(),
        channel.getDescription().toString(),
        channel.getImageUrl().toString(),
        categoryIds
    );
  }
}
