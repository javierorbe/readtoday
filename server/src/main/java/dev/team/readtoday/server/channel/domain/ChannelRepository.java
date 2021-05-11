package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
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

  /**
   * Returns a collection of channels given their ids.
   *
   * @param channelIds a collection of channel ids.
   * @return a collection of channels.
   */
  Collection<Channel> get(Collection<ChannelId> channelIds);

  /**
   * Returns a collection of channels that belong to a given category.
   *
   * @param categoryId id of the category.
   * @return a collection of channels.
   */
  Collection<Channel> getByCategory(CategoryId categoryId);
}
