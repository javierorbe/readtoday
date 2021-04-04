package dev.team.readtoday.server.channel.domain;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public final class Channel {

  private final ChannelId id;
  private final ChannelTitle title;
  private final RssUrl rssUrl;
  private final ChannelDescription description;
  private final ImageUrl imageUrl;
  private final Collection<CategoryId> categories;

  public Channel(
      ChannelId id,
      ChannelTitle title,
      RssUrl rssUrl,
      ChannelDescription description,
      ImageUrl imageUrl,
      Collection<CategoryId> categories) {

    Objects.requireNonNull(title);
    Objects.requireNonNull(rssUrl);
    Objects.requireNonNull(description);
    Objects.requireNonNull(imageUrl);
    Objects.requireNonNull(categories);

    this.id = id;
    this.title = title;
    this.imageUrl = imageUrl;
    this.rssUrl = rssUrl;
    this.description = description;
    this.categories = new ArrayList<>(categories);
  }

  public static Channel create(
      ChannelTitle title,
      RssUrl rssUrl,
      ChannelDescription description,
      ImageUrl imageUrl,
      Collection<CategoryId> categoryIds) {
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

  public ImmutableCollection<CategoryId> getCategories() {
    return ImmutableSet.copyOf(categories);
  }

  public void addCategory(CategoryId categoryId) {
    categories.add(categoryId);
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
