package dev.team.readtoday.server.publication.application.create;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;

@Service
public class CreatePublication {
  private PublicationRepository repository;

  public CreatePublication(
      PublicationRepository repository) {
    this.repository = repository;
  }

  public void create(PublicationId pubId,
      PublicationTitle pubTit,
      PublicationDescription pubDes,
      PublicationDate pubDat,
      PublicationLink pubLink,
      Collection<CategoryId> cat){
    Publication publication = new Publication(pubId, pubTit,pubDes, pubDat, pubLink, cat);
    repository.save(publication);
  }
}
