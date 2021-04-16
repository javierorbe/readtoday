package dev.team.readtoday.server.publication.application;

import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.RssFeedException;
import java.util.List;

@FunctionalInterface
public interface RssFetcher {

  List<Publication> getPublications(RssUrl rssUrl) throws RssFeedException;
}
