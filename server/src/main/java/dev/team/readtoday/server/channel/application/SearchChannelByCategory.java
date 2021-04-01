package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.List;

public final class SearchChannelByCategory {

  private final ChannelRepository channelRepository;

  public SearchChannelByCategory(
      ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  public List<Channel> getAllByCategoryId(String categoryId) {
    return channelRepository.getAllByCategoryId(CategoryId.fromString(categoryId));
  }

  public List<Channel> getAllByCategoryName(String categoryName) {
    return channelRepository.getAllByCategoryName(new CategoryName(categoryName));
  }
}
