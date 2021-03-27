package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.category.domain.Category;
import java.util.List;
import java.util.Objects;

public final class Channel {

  private final ChannelId id;
  private final ChannelTitle title;
  private final Url rssUrl;
  private final ChannelDescription description;
  private final Url imageUrl;
  private final List<Category> categories;

  public Channel(
      ChannelId id,
      ChannelTitle title,
      Url rssUrl,
      ChannelDescription description,
      Url imageUrl,
      List<Category> categories) {

    Objects.requireNonNull(title);
    Objects.requireNonNull(rssUrl);
    Objects.requireNonNull(description);
    Objects.requireNonNull(imageUrl);
    Objects.requireNonNull(categories);

    this.id = id;
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categories = categories;
  }

  public static Channel create(
      ChannelTitle title,
      Url rssUrl,
      ChannelDescription description,
      Url imageUrl,
      List<Category> categories) {
    return new Channel(ChannelId.random(), title, rssUrl, description, imageUrl, categories);
  }

  public ChannelId getId() {
    return id;
  }

  public ChannelTitle getTitle() {
    return title;
  }

  public Url getRssUrl() {
    return rssUrl;
  }

  public ChannelDescription getDescription() {
    return description;
  }

  public Url getImageUrl() {
    return imageUrl;
  }

  public List<Category> getCategories() {
    return categories;
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
