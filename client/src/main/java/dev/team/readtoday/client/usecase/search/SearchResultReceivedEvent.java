package dev.team.readtoday.client.usecase.search;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.model.Channel;

public final class SearchResultReceivedEvent {

  private final ImmutableCollection<Channel> channels;

  SearchResultReceivedEvent(ImmutableCollection<Channel> channels) {
    this.channels = channels;
  }

  public ImmutableCollection<Channel> getChannels() {
    return channels;
  }
}
