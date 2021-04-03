package dev.team.readtoday.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class CategoryTest {

  @Test
  void shouldBeSortedByName() {
    Collection<Category> categories = Set.of(
        CategoryMother.withName("Science"),
        CategoryMother.withName("News"),
        CategoryMother.withName("Technology")
    );

    ImmutableList<Category> sortedChannels = ImmutableList.sortedCopyOf(categories);

    ImmutableList<Category> expectedOrder = ImmutableList.sortedCopyOf(
        (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()), categories);
    assertEquals(expectedOrder, sortedChannels);
  }
}
