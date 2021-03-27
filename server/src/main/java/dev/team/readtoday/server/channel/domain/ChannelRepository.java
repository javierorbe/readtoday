package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.category.domain.CategoryName;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository {

  void save(Channel channel);

  Optional<List<Channel>> getAllByCategoryName(CategoryName categoryName);

  Optional<Channel> getFromId(ChannelId channelId);
}
