package dev.team.readtoday.server.publication.application;

import dev.team.readtoday.server.channel.application.SearchChannel;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.List;

@Service
public final class SearchPublications {

  private final SearchChannel searchChannel;

  public SearchPublications(SearchChannel searchChannel) {
    this.searchChannel = searchChannel;
  }

  public List<Publication> search(ChannelId channelId) {
    // TODO
    throw new UnsupportedOperationException("Not implemented, yet.");
  }
}
