package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.category.domain.CategoryId;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository {

  void save(Channel channel);

  Optional<List<Channel>> getAllByCategoryId(CategoryId categoryId);
}
