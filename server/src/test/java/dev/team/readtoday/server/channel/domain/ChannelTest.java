package dev.team.readtoday.server.channel.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class ChannelTest {

  @Test
  void shouldBeEqualIfSameInstance() {
    Channel channel = ChannelMother.random();
    assertEquals(channel, channel);
  }

  @Test
  void shouldNotBeEqualToNull() {
    Channel channel = ChannelMother.random();
    assertFalse(channel.equals(null));
  }

  @Test
  void shouldBeEqualIfSameId() {
    Channel expectedChannel = ChannelMother.random();
    Channel channel = ChannelMother.withId(expectedChannel.getId());
    assertEquals(expectedChannel, channel);
  }
}
