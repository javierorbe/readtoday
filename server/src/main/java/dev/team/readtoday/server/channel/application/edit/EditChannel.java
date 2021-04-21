package dev.team.readtoday.server.channel.application.edit;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Optional;

@Service
public class EditChannel {

  private final ChannelRepository repository;

  public EditChannel(ChannelRepository repository) {
    this.repository = repository;
  }

  public void edit(Channel channel) {

    Optional<Channel> optChannel = repository.getFromId(channel.getId());

    if (optChannel.isEmpty()) {
      throw new ChannelNotFound();
    }

    repository.save(channel);
  }
}
