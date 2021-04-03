package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Collection;
import java.util.Optional;

public interface ChannelRepository {

  /**
   * Create or update a channel.
   *
   * <p>Category integrity is checked.
   */
  void save(Channel channel);

  /** Returns a Channel given a channel id. */
  Optional<Channel> getFromId(ChannelId channelId);

  Collection<Channel> getByCategory(CategoryId categoryId);
}
