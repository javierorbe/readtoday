package dev.team.readtoday.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class ChannelTest {

  @Test
  void shouldBeSortedByName() {
    Collection<Channel> channels = Set.of(
        ChannelMother.withName("The New York Times"),
        ChannelMother.withName("TechCrunch"),
        ChannelMother.withName("Hacker News")
    );

    ImmutableList<Channel> sortedChannels = ImmutableList.sortedCopyOf(channels);

    ImmutableList<Channel> expectedOrder = ImmutableList.sortedCopyOf(
        (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()), channels);
    assertEquals(expectedOrder, sortedChannels);
  }
}
