package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.List;
import java.util.Objects;

public final class Channel {

  private final ChannelId id;
  private final ChannelTitle title;
  private final RssUrl rssUrl;
  private final ChannelDescription description;
  private final ImageUrl imageUrl;
  private final List<CategoryId> categoryIds;

  public Channel(
      ChannelId id,
      ChannelTitle title,
      RssUrl rssUrl,
      ChannelDescription description,
      ImageUrl imageUrl,
      List<CategoryId> categoryIds) {

    Objects.requireNonNull(title);
    Objects.requireNonNull(rssUrl);
    Objects.requireNonNull(description);
    Objects.requireNonNull(imageUrl);
    Objects.requireNonNull(categoryIds);

    this.id = id;
    this.title = title;
    this.imageUrl = imageUrl;
    this.rssUrl = rssUrl;
    this.description = description;
    this.categoryIds = categoryIds;
  }

  public static Channel create(
      ChannelTitle title,
      RssUrl rssUrl,
      ChannelDescription description,
      ImageUrl imageUrl,
      List<CategoryId> categoryIds) {
    return new Channel(ChannelId.random(), title, rssUrl, description, imageUrl, categoryIds);
  }

  public ChannelId getId() {
    return id;
  }

  public ChannelTitle getTitle() {
    return title;
  }

  public RssUrl getRssUrl() {
    return rssUrl;
  }

  public ChannelDescription getDescription() {
    return description;
  }

  public ImageUrl getImageUrl() {
    return imageUrl;
  }

  public List<CategoryId> getCategoryIds() {
    return categoryIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Channel)) {
      return false;
    }
    Channel channel = (Channel) o;
    return Objects.equals(id, channel.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
