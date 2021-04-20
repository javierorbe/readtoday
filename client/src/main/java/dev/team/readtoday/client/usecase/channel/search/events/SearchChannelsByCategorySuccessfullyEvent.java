package dev.team.readtoday.client.usecase.channel.search.events;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.model.Channel;

public final class SearchChannelsByCategorySuccessfullyEvent {

  private final ImmutableCollection<Channel> channels;

  public SearchChannelsByCategorySuccessfullyEvent(ImmutableCollection<Channel> channels) {
    this.channels = channels;
  }

  public ImmutableCollection<Channel> getChannels() {
    return channels;
  }
}
