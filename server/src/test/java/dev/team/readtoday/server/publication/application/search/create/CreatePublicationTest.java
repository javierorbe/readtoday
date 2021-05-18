package dev.team.readtoday.server.publication.application.search.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import dev.team.readtoday.server.publication.application.create.CreatePublication;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class CreatePublicationTest {

  @Test
  void shouldCreatePublication(){
    PublicationRepository repository = mock(PublicationRepository.class);
    CreatePublication createPublication = new CreatePublication(repository);
    PublicationId userId = PublicationId.random();
    PublicationTitle pubTit = new PublicationTitle("a");
    PublicationDescription pubDes = new PublicationDescription("a");
    PublicationDate pubDat = new PublicationDate(null);
    PublicationLink pubLink = new PublicationLink("a");
    Collection<CategoryId> cat = new ArrayList<>();

    createPublication.create(userId, pubTit,pubDes,pubDat,pubLink,cat);

    var publicationCaptor = ArgumentCaptor.forClass(Publication.class);
    verify(repository).save(publicationCaptor.capture());
    assertEquals(userId, publicationCaptor.getValue().getId());


  }
}
