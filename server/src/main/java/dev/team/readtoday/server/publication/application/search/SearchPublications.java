package dev.team.readtoday.server.publication.application.search;

import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.application.RssFetcher;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.List;
import java.util.function.Function;

@Service
public final class SearchPublications implements Function<List<RssUrl>, List<Publication>> {

  private final RssFetcher rssFetcher;

  public SearchPublications(RssFetcher rssFetcher) {
    this.rssFetcher = rssFetcher;
  }

  @Override
  public List<Publication> apply(List<RssUrl> rssUrls) {
    return rssUrls.stream().flatMap(url -> rssFetcher.getPublications(url).stream()).toList();
  }
}
