package dev.team.readtoday.client;

import java.util.UUID;

public final class Channel {

  private final UUID id;
  private final String name;
  private final String faviconImageUrl;

  public Channel(UUID id, String name, String faviconImageUrl) {
    this.id = id;
    this.name = name;
    this.faviconImageUrl = faviconImageUrl;
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
