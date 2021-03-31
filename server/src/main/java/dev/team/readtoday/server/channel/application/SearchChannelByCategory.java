package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.List;

public class SearchChannelByCategory {

  private final ChannelRepository channelRepository;

  public SearchChannelByCategory(
      ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  public List<Channel> getAllByCategoryId(String categoryId) {
    return channelRepository.getAllByCategoryId(CategoryId.fromString(categoryId));
  }
}
