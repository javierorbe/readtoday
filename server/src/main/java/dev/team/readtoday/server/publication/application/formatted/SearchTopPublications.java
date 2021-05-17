package dev.team.readtoday.server.publication.application.formatted;

import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.application.RssFetcher;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.List;
import java.util.function.Function;

@Service
public final class SearchTopPublications implements Function<List<RssUrl>, List<Publication>> {

  private final RssFetcher rssFetcher;

  public SearchTopPublications(RssFetcher rssFetcher) {
    this.rssFetcher = rssFetcher;
  }

  @Override
  public List<Publication> apply(List<RssUrl> rssUrls) {
    return rssUrls.stream().map(url -> rssFetcher.getPublications(url).get(0)).toList();
  }
}
