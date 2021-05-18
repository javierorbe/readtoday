package dev.team.readtoday.server.customlist.infrastructure.controller.get;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListGetPublicationsResponseTest {

  @Test
  void shouldGetCorrectly() {
    Collection<Publication> publications = Arrays.asList(
        PublicationMother.random(),
        PublicationMother.random());

    CustomListGetPublicationsResponse response = new CustomListGetPublicationsResponse(
        publications);

    assertEquals(publications, response.getPublications());
  }
}
