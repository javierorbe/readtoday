package dev.team.readtoday.server.customlist.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import dev.team.readtoday.server.shared.domain.CustomListId;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class AddPublicationTest {

  @Test
  void shouldAddPublication() {
    CustomListRepository repository = mock(CustomListRepository.class);
    AddPublication addPublication = new AddPublication(repository);
    CustomListId customListId = CustomListId.random();
    Publication publication = PublicationMother.random();
    assertDoesNotThrow(() -> addPublication.add(customListId,publication.getId()));
  }
}
