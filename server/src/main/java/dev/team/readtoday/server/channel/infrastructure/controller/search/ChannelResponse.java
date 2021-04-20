package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.Identifier;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ChannelResponse {

  private final String id;
  private final String title;
  private final String rssUrl;
  private final String description;
  private final String imageUrl;
  private final List<String> categoryIds;

  private ChannelResponse(Channel channel) {
    id = channel.getId().toString();
    title = channel.getTitle().toString();
    rssUrl = channel.getRssUrl().toString();
    description = channel.getDescription().toString();
    imageUrl = channel.getImageUrl().toString();
    categoryIds = channel.getCategories().stream()
        .map(Identifier::toString)
        .collect(Collectors.toList());
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

  public List<String> getCategoryIds() {
    return categoryIds;
  }

  public static List<ChannelResponse> fromChannels(Collection<Channel> channels) {
    return channels.stream()
        .map(ChannelResponse::new)
        .collect(Collectors.toList());
  }
}
