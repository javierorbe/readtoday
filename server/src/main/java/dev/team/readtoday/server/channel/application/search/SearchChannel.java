package dev.team.readtoday.server.channel.application.search;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;
import java.util.function.Function;

@Service
public final class SearchChannel
    implements Function<Collection<ChannelId>, Collection<Channel>> {

  private final ChannelRepository repository;

  public SearchChannel(ChannelRepository repository) {
    this.repository = repository;
  }

  @Override
  public Collection<Channel> apply(Collection<ChannelId> channelIds) {
    return repository.get(channelIds);
  }
}
