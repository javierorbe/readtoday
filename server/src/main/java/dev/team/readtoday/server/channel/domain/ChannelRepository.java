package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository {

  void save(Channel channel);

  List<Channel> getAllByCategoryId(CategoryId categoryId);

  Optional<Channel> getFromId(ChannelId channelId);
}
