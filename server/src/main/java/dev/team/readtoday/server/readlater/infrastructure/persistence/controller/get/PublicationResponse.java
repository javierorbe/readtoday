package dev.team.readtoday.server.readlater.infrastructure.persistence.controller.get;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.shared.domain.StringValueObject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;


public class PublicationResponse {

  private final String id;
  private final String title;
  private final String description;
  private final OffsetDateTime date;
  private final String link;


  PublicationResponse(Publication publication) {
    id = publication.getId().toString();
    title = publication.getTitle().map(StringValueObject::toString).orElse(null);
    date = publication.getDate().map(PublicationDate::getDateTime).orElse(null);
    description = publication.getDescription().map(StringValueObject::toString).orElse(null);
    link = publication.getLink().map(StringValueObject::toString).orElse(null);

  }

  public static List<PublicationResponse> fromPublication(Collection<Publication> publications) {
    return publications.stream().map(pub -> new PublicationResponse(pub)).toList();
  }
}


