package dev.team.readtoday.client.model;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.UUID;

public final class Channel implements Comparable<Channel> {

  private final UUID id;
  private final String name;
  private final String faviconImageUrl;
  private final ImmutableCollection<Category> categories;

  public Channel(UUID id, String name, String faviconImageUrl, Collection<Category> categories) {
    this.id = id;
    this.name = name;
    this.faviconImageUrl = faviconImageUrl;
    this.categories = ImmutableSet.copyOf(categories);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getFaviconImageUrl() {
    return faviconImageUrl;
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
