package dev.team.readtoday.server.publication.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.PUBLICATION;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.PUBLICATION_CATEGORIES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.publication.infrastructure.persistance.JooqPublicationRepository;


@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
public class JooqPublicationRepositoryTest extends BaseJooqIntegrationTest {

  private static PublicationRepository repository;

  @BeforeAll
  static void beforeAll() {
    start(PUBLICATION_CATEGORIES, PUBLICATION);
    repository = getRepository(JooqPublicationRepository.class);
  }

  @AfterAll
  static void afterAll() {
    clearAndShutdown();
  }

  @Test
  void shouldSavePublication() {
    Publication publication = PublicationMother.random();

    assertDoesNotThrow(() -> repository.save(publication));
  }

  @Test
  void shouldReturnExistingPublication() {
    Publication savedPublication = PublicationMother.random();

    repository.save(savedPublication);

    Optional<Publication> optPublication = repository.getFromId(savedPublication.getId());
    assertTrue(optPublication.isPresent());
    Publication publication = optPublication.get();

    assertEquals(savedPublication.getId(), publication.getId());
    assertEquals(savedPublication.getTitle(), publication.getTitle());
    assertEquals(savedPublication.getDescription(), publication.getDescription());
    assertEquals(savedPublication.getDate().get()/* .getDateTime() */,
        publication.getDate().get()/* .getDateTime() */);
    assertEquals(savedPublication.getLink(), publication.getLink());
  }

}
