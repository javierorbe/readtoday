package dev.team.readtoday.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class ExampleTest {

  @Test
  void twoPlusTwoIsFour() {
    assertEquals(4, 2 + 2);
  }
}
