package dev.team.readtoday.server.publication.application;

import dev.team.readtoday.server.channel.application.SearchChannel;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.RssFeedException;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.List;

@Service
public final class SearchPublications {

  private final SearchChannel searchChannel;
  private final RssFetcher publicationRetriever;

  public SearchPublications(SearchChannel searchChannel,
                            RssFetcher publicationRetriever) {
    this.searchChannel = searchChannel;
    this.publicationRetriever = publicationRetriever;
  }

  public List<Publication> search(ChannelId channelId) throws RssFeedException {
    Channel channel = searchChannel.search(channelId);
    return publicationRetriever.getPublications(channel.getRssUrl());
  }
}
