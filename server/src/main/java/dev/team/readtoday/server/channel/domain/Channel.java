package dev.team.readtoday.server.channel.domain;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a Rss Channel.
 */
public final class Channel {

  /**
   * Represents the id (UUID) of the channel
   */
  private final ChannelId id;
  /**
   * Represents the title or the name of the channel.
   */
  private final ChannelTitle title;
  /**
   * Represents the link or url to the HTML website corresponding to the channel.
   */
  private final RssUrl rssUrl;
  /**
   * Represents the phrase or sentence describing the channel.
   */
  private final ChannelDescription description;
  /**
   * Represents the url of a GIF, JPEG or PNG image that represents the channel.
   */
  private final ImageUrl imageUrl;
  /**
   * Represents channel's categories.
   */
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

  /**
   * Returns a channel instance, it manages to create a random id to the channel.
   *
   * @param title       the title or name of the new channel.
   * @param rssUrl      the link or url to the HTML website corresponding to the channel.
   * @param description phrase or sentence describing the channel.
   * @param imageUrl    the image url of the channel.
   * @param categoryIds list of category ids.
   * @return a new channel with properties passed.
   */
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
