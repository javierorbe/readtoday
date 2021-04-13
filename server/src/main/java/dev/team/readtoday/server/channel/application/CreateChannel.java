package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;

@Service
public final class CreateChannel {

  private final ChannelRepository repository;

  public CreateChannel(ChannelRepository repository) {
    this.repository = repository;
  }

  public Channel create(ChannelTitle title,
                     RssUrl rssUrl,
                     ChannelDescription description,
                     ImageUrl imageUrl,
                     Collection<CategoryId> categories) {
    Channel channel = Channel.create(title, rssUrl, description, imageUrl, categories);
    repository.save(channel);
    return channel;
  }
}
