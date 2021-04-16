package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Optional;

@Service
public final class SearchChannel {

  private final ChannelRepository repository;

  public SearchChannel(ChannelRepository repository) {
    this.repository = repository;
  }

  public Channel search(ChannelId id) {
    Optional<Channel> optChannel = repository.getFromId(id);
    return optChannel.orElseThrow(ChannelNotFound::new);
  }
}
