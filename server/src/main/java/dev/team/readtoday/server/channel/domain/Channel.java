package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.List;
import java.util.Objects;

public final class Channel {

  private final ChannelId id;
  private final ChannelTitle title;
  private final RssUrl rssURL;
  private final ChannelDescription description;
  private final ImageUrl imageURL;
  private final List<CategoryId> categoryIds;

  public Channel(
      ChannelId id,
      ChannelTitle title,
      RssUrl rssURL,
      ChannelDescription description,
      ImageUrl imageURL,
      List<CategoryId> categoryIds) {

    Objects.requireNonNull(title);
    Objects.requireNonNull(rssURL);
    Objects.requireNonNull(description);
    Objects.requireNonNull(imageURL);
    Objects.requireNonNull(categoryIds);

    this.id = id;
    this.title = title;
    this.imageURL = imageURL;
    this.rssURL = rssURL;
    this.description = description;
    this.categoryIds = categoryIds;
  }

  public static Channel create(
      ChannelTitle title,
      RssUrl rssURL,
      ChannelDescription description,
      ImageUrl imageURL,
      List<CategoryId> categoryIds) {
    return new Channel(ChannelId.random(), title, rssURL, description, imageURL, categoryIds);
  }

  public ChannelId getId() {
    return id;
  }

  public ChannelTitle getTitle() {
    return title;
  }

  public RssUrl getRssUrl() {
    return rssURL;
  }

  public ChannelDescription getDescription() {
    return description;
  }

  public ImageUrl getImageUrl() {
    return imageURL;
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
