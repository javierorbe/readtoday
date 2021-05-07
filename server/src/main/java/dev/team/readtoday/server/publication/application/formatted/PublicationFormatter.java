package dev.team.readtoday.server.publication.application.formatted;

import dev.team.readtoday.server.publication.domain.Publication;

public interface PublicationFormatter {

  String format(Publication publication);
}
