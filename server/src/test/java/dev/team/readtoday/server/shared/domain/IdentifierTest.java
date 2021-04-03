package dev.team.readtoday.server.shared.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class IdentifierTest {

  @Test
  void shouldBeEqualIfSameInstance() {
    Identifier identifier = new Identifier(UUID.randomUUID());
    assertEquals(identifier, identifier);
  }

  @Test
  void shouldNotBeEqualToNull() {
    Identifier identifier = new Identifier(UUID.randomUUID());
    assertNotEquals(null, identifier);
  }
}
