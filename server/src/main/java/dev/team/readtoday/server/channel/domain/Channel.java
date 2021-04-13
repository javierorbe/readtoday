package dev.team.readtoday.server.channel.domain;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.Collection;
import java.util.HashSet;
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
    this.id = Objects.requireNonNull(id);
    this.title = Objects.requireNonNull(title);
    this.rssUrl = Objects.requireNonNull(rssUrl);
    this.description = Objects.requireNonNull(description);
    this.imageUrl = Objects.requireNonNull(imageUrl);
    this.categories = new HashSet<>(categories);
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
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    Channel channel = (Channel) o;
    return id.equals(channel.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
