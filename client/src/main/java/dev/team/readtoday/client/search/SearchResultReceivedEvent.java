package dev.team.readtoday.client.search;

import dev.team.readtoday.client.model.Channel;
import java.util.Collections;
import java.util.List;

public final class SearchResultReceivedEvent {

  private final List<Channel> channels;

  public SearchResultReceivedEvent(List<Channel> channels) {
    this.channels = channels;
  }

  public List<Channel> getChannels() {
    return Collections.unmodifiableList(channels);
  }
}
