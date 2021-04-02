package dev.team.readtoday.server.channel.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.category.infrastructure.persistence.JooqCategoryRepository;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqChannelRepositoryTest {

  private static JooqConnectionBuilder jooq;
  private static ChannelRepository channelRepository;
  private static CategoryRepository categoryRepository;
  private static DSLContext ctx;

  @BeforeAll
  static void setup() {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    channelRepository = new JooqChannelRepository(jooq.getContext());
    categoryRepository = new JooqCategoryRepository(jooq.getContext());

    ctx = jooq.getContext();
    ctx.deleteFrom(CHANNEL_CATEGORIES).execute();
    ctx.deleteFrom(CHANNEL).execute();
  }

  @Test
  void shouldSaveChannel() throws MalformedURLException {
    Channel channel = ChannelMother.random();

    assertDoesNotThrow(() -> channelRepository.save(channel));
  }

  @Test
  void shouldReturnAnExistingChannel() throws MalformedURLException {
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

  @Test
  void shouldReturnChannelsByCategoryName() throws MalformedURLException {
    // Create random categories
    Category category1 = CategoryMother.random();
    Category category2 = CategoryMother.random();

    categoryRepository.save(category1);
    categoryRepository.save(category2);

    // Bind categories with a random channels
    List<CategoryId> categoryIds = Arrays.asList(category1.getId(), category2.getId());

    Channel originalChannel = ChannelMother.randomWithCategories(categoryIds);

    channelRepository.save(originalChannel);

    // Verify if channels are returned by category correctly
    List<Channel> channelsResult = channelRepository.getAllByCategoryName(category1.getName());

    // Get position 0 because of just a channel is created randomly
    Channel channel = channelsResult.get(0);

    // Verify channel attributes
    assertEquals(originalChannel.getId(), channel.getId());
    assertEquals(originalChannel.getTitle(), channel.getTitle());
    assertEquals(originalChannel.getRssUrl(), channel.getRssUrl());
    assertEquals(originalChannel.getDescription(), channel.getDescription());
    assertEquals(originalChannel.getImageUrl(), channel.getImageUrl());

    // Verify channel category binding
    assertEquals(originalChannel.getCategoryIds(), categoryIds);
  }


  @AfterAll
  static void clean() {
    ctx.deleteFrom(CHANNEL_CATEGORIES).execute();
    ctx.deleteFrom(CHANNEL).execute();
    jooq.close();
  }
}
