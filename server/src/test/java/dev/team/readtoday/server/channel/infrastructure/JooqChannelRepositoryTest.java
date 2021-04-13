package dev.team.readtoday.server.channel.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqChannelRepositoryTest extends BaseJooqIntegrationTest {

  private static ChannelRepository channelRepository;

  @BeforeAll
  static void beforeAll() {
    start(CHANNEL_CATEGORIES, CHANNEL);
    channelRepository = getRepository(JooqChannelRepository.class);
  }

  @AfterAll
  static void afterAll() {
    clearAndShutdown();
  }

  @Test
  void shouldSaveChannel() {
    Channel channel = ChannelMother.random();

    assertDoesNotThrow(() -> channelRepository.save(channel));
  }

  @Test
  void shouldReturnAnExistingChannel() {
    Channel originalChannel = ChannelMother.random();

    channelRepository.save(originalChannel);

    Optional<Channel> optChannel = channelRepository.getFromId(originalChannel.getId());
    assertTrue(optChannel.isPresent());
    Channel channel = optChannel.get();

    assertEquals(originalChannel.getId(), channel.getId());
    assertEquals(originalChannel.getTitle(), channel.getTitle());
    assertEquals(originalChannel.getRssUrl(), channel.getRssUrl());
    assertEquals(originalChannel.getDescription(), channel.getDescription());
    assertEquals(originalChannel.getImageUrl(), channel.getImageUrl());
  }

  @Test
  void shouldNotReturnANonExistingChannel() {
    Optional<Channel> optChannel = channelRepository.getFromId(ChannelId.random());
    assertTrue(optChannel.isEmpty());
  }
}
