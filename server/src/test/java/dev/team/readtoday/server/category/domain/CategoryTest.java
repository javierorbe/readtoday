package dev.team.readtoday.server.category.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class CategoryTest {

  @Test
  void shouldBeEqualIfSameInstance() {
    Category expectedCategory = CategoryMother.random();
    assertEquals(expectedCategory, expectedCategory);
  }

  @Test
  void shouldNotBeEqualToNull() {
    Category category = CategoryMother.random();
    assertFalse(category.equals(null));
  }
}
