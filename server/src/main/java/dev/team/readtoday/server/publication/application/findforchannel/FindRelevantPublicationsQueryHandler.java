package dev.team.readtoday.server.publication.application.findforchannel;

import dev.team.readtoday.server.category.application.search.SearchCategoryQuery;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Identifier;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindRelevantPublicationsQueryHandler
    implements QueryHandler<FindRelevantPublicationsQuery, FindRelevantPublicationsResponse> {

  private final QueryBus queryBus;
  private final FindRelevantPublications finder;

  public FindRelevantPublicationsQueryHandler(QueryBus queryBus, FindRelevantPublications finder) {
    this.queryBus = queryBus;
    this.finder = finder;
  }

  @Override
  public FindRelevantPublicationsResponse handle(FindRelevantPublicationsQuery query) {
    Collection<String> ids = query.getChannelIds();
    List<ChannelId> channelIds = ids.stream()
        .map(ChannelId::fromString)
        .collect(Collectors.toList());
    List<Publication> publications = finder.apply(channelIds);

    return buildResponse(publications);
  }

  private FindRelevantPublicationsResponse buildResponse(List<Publication> publications) {
    var categoryResponse = queryBus.ask(new SearchCategoryQuery(getCategoryIds(publications)));
    var publicationResponse =
        PublicationResponse.fromDomain(publications, categoryResponse.getCategories());

    return new FindRelevantPublicationsResponse(publicationResponse);
  }

  private static Collection<String> getCategoryIds(Collection<Publication> publications) {
    return publications.stream()
        .flatMap(pub -> pub.getCategories().stream())
        .map(Identifier::toString)
        .collect(Collectors.toUnmodifiableSet());
  }
}
