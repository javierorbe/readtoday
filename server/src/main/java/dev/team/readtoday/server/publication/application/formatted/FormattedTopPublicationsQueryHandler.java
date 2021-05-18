package dev.team.readtoday.server.publication.application.formatted;

import dev.team.readtoday.server.channel.application.search.ChannelResponse;
import dev.team.readtoday.server.channel.application.search.SearchChannelQuery;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.application.FormattedPublicationsResponse;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.util.Collection;
import java.util.List;

@Service
public final class FormattedTopPublicationsQueryHandler
    implements QueryHandler<FormattedTopPublicationsQuery, FormattedPublicationsResponse> {

  private final QueryBus queryBus;

  private final SearchTopPublications searchTopPublications;
  private final PublicationFormatter formatter;

  public FormattedTopPublicationsQueryHandler(QueryBus queryBus,
                                              SearchTopPublications topPublications,
                                              PublicationFormatter formatter) {
    this.queryBus = queryBus;
    this.searchTopPublications = topPublications;
    this.formatter = formatter;
  }

  @Override
  public FormattedPublicationsResponse handle(FormattedTopPublicationsQuery command) {
    List<Publication> publications = getPublications(command);
    List<String> formattedPubs = publications.stream().map(formatter::format).toList();
    return new FormattedPublicationsResponse(formattedPubs);
  }

  private List<Publication> getPublications(FormattedTopPublicationsQuery command) {
    List<RssUrl> rssUrls = getRssUrls(command);
    return searchTopPublications.apply(rssUrls);
  }

  private List<RssUrl> getRssUrls(FormattedTopPublicationsQuery command) {
    Collection<ChannelResponse> channels = getChannels(command);
    return channels.stream().map(channel -> new RssUrl(channel.getRssUrl())).toList();
  }

  private Collection<ChannelResponse> getChannels(FormattedTopPublicationsQuery command) {
    List<String> channelIds = command.getChannelIds();
    var channelsResponse = queryBus.ask(new SearchChannelQuery(channelIds));
    return channelsResponse.getChannels();
  }
}
