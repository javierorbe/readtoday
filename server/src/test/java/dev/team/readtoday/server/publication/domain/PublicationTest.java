package dev.team.readtoday.server.publication.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.PublicationIdMother;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class PublicationTest {

  @Test
  void shouldThrowExceptionIfNeitherTitleOrDescriptionIsPresent() {
    PublicationId id = PublicationIdMother.random();
    assertThrows(InvalidPublication.class,
        () -> new Publication(id, null, null, null, null, Set.of()));
  }

  @Test
  void shouldAllowNullOptionalParameters() {
    PublicationId id = PublicationIdMother.random();
    PublicationTitle title = PublicationTitleMother.random();
    PublicationDescription description = PublicationDescriptionMother.random();
    assertDoesNotThrow(() -> new Publication(id, title, null, null, null, Set.of()));
    assertDoesNotThrow(() -> new Publication(id, null, description, null, null, Set.of()));
  }
}
