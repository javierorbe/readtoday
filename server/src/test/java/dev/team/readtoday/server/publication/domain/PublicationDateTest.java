package dev.team.readtoday.server.publication.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class PublicationDateTest {

  @Test
  void shouldEqualIfSameInstance() {
    PublicationDate date = new PublicationDate(OffsetDateTime.now());
    assertEquals(date, date);
  }

  @Test
  void shouldNotEqualToNull() {
    PublicationDate date = new PublicationDate(OffsetDateTime.now());
    assertFalse(date.equals(null));
  }
}
