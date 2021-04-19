package dev.team.readtoday.server.publication.application.search;

import dev.team.readtoday.server.category.application.search.SearchCategoryQuery;
import dev.team.readtoday.server.channel.application.search.ChannelResponse;
import dev.team.readtoday.server.channel.application.search.SearchChannelQuery;
import dev.team.readtoday.server.channel.application.search.SearchChannelQueryResponse;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.Identifier;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class SearchPublicationsQueryHandler implements
    QueryHandler<SearchPublicationsQuery, SearchPublicationsResponse> {

  private final QueryBus queryBus;
  private final SearchPublications searchPublications;

  public SearchPublicationsQueryHandler(QueryBus queryBus, SearchPublications searchPublications) {
    this.queryBus = queryBus;
    this.searchPublications = searchPublications;
  }

  @Override
  public SearchPublicationsResponse handle(SearchPublicationsQuery query) {
    List<RssUrl> urls = getChannelRssUrls(query);
    List<Publication> publications = searchPublications.apply(urls);
    return buildResponse(publications);
  }

  private SearchPublicationsResponse buildResponse(List<Publication> publications) {
    var categoryResponse = queryBus.ask(new SearchCategoryQuery(getCategoryIds(publications)));
    var publicationResponse =
        PublicationResponse.fromDomain(publications, categoryResponse.getCategories());
    return new SearchPublicationsResponse(publicationResponse);
  }

  private List<RssUrl> getChannelRssUrls(SearchPublicationsQuery query) {
    SearchChannelQueryResponse resp = queryBus.ask(new SearchChannelQuery(query.getChannelIds()));
    Collection<ChannelResponse> channels = resp.getChannels();
    return channels.stream().map(channel -> new RssUrl(channel.getRssUrl())).toList();
  }

  private static Collection<String> getCategoryIds(Collection<Publication> publications) {
    return publications.stream()
        .flatMap(pub -> pub.getCategories().stream())
        .map(Identifier::toString)
        .collect(Collectors.toUnmodifiableSet());
  }
}
