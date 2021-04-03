package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.Identifier;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ChannelResponse {

  private String id;
  private String title;
  private String rssUrl;
  private String description;
  private String imageUrl;
  private List<String> categories;

  private ChannelResponse(String id,
                          String title,
                          String rssUrl,
                          String description,
                          String imageUrl,
                          List<String> categories) {
    this.id = id;
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categories = categories;
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

  public List<String> getCategories() {
    return categories;
  }

  private static ChannelResponse fromChannel(Channel channel) {
    List<String> categories = channel.getCategories().stream()
        .map(Identifier::toString)
        .collect(Collectors.toList());

    return new ChannelResponse(
        channel.getId().toString(),
        channel.getTitle().toString(),
        channel.getRssUrl().toString(),
        channel.getDescription().toString(),
        channel.getImageUrl().toString(),
        categories
    );
  }

  static List<ChannelResponse> fromChannels(Collection<Channel> channels) {
    return channels.stream()
        .map(ChannelResponse::fromChannel)
        .collect(Collectors.toList());
  }
}
