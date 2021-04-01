package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;

public class CreateChannel {

  private final ChannelRepository channelRepository;

  public CreateChannel(ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  public void createChannel(Channel channel) {
    channelRepository.save(channel);
  }
}
