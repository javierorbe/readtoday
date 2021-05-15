package dev.team.readtoday.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class PublicationTest {
  
  @Test
  void shouldBeShortedByName(){
    Collection<Publication> publications = Set.of(
        PublicationMother.withName("Economic news"),
        PublicationMother.withName("Political news")
        );
    ImmutableList<Publication> sortedPublications = ImmutableList.sortedCopyOf(publications);

    ImmutableList<Publication> expectedOrder = ImmutableList.sortedCopyOf(
        (c1, c2) -> c1.getTitle().compareToIgnoreCase(c2.getTitle()), publications);
    assertEquals(expectedOrder, sortedPublications);
  }


}
