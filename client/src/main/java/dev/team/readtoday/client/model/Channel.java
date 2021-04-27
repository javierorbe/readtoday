package dev.team.readtoday.client.model;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public final class Channel implements Comparable<Channel> {

  private final String id;
  private final String name;
  private final URL rssUrl;
  private final String description;
  private final URL faviconImageUrl;
  private final ImmutableCollection<Category> categories;

  public Channel(String id,
      String name,
      String rssUrl,
      String description,
      String faviconImageUrl,
      Collection<Category> categories) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.categories = ImmutableSet.copyOf(categories);
    try {
      this.faviconImageUrl = new URL(faviconImageUrl);
      this.rssUrl = new URL(rssUrl);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Invalid URL argument.", e);
    }
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getFaviconImageUrl() {
    return faviconImageUrl.toString();
  }

  public String getRssUrl() {
    return rssUrl.toString();
  }

  public String getDescription() {
    return description;
  }

  public ImmutableCollection<Category> getCategories() {
    return categories;
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

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(Channel other) {
    return name.compareToIgnoreCase(other.name);
  }
}
