package dev.team.readtoday.server.channel.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.impl.DSL;

@Service
public final class JooqChannelRepository implements ChannelRepository {

  private final DSLContext dsl;

  public JooqChannelRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Channel channel) {
    dsl.transaction(configuration -> {
      saveChannel(configuration, channel);
      saveChannelCategories(configuration, channel);
    });
  }

  @Override
  public Optional<Channel> getFromId(ChannelId channelId) {
    Record5<String, String, String, String, String> channelResult =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.RSS_URL, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .where(CHANNEL.ID.eq(channelId.toString()))
            .fetchOne();

    if (channelResult == null) {
      return Optional.empty();
    }

    Channel channel = createChannelFromResult(channelResult);
    return Optional.of(channel);

  }

  @Override
  public Collection<Channel> getByCategory(CategoryId categoryId) {
    var result =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.RSS_URL, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .join(CHANNEL_CATEGORIES).on(CHANNEL_CATEGORIES.CHANNEL_ID.eq(CHANNEL.ID))
            .where(CHANNEL_CATEGORIES.CATEGORY_ID.eq(categoryId.toString()))
            .fetch();

    return result.stream()
        .map(this::createChannelFromResult)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static void saveChannel(Configuration configuration, Channel channel) {
    DSL.using(configuration)
        .insertInto(CHANNEL, CHANNEL.ID, CHANNEL.TITLE,
            CHANNEL.RSS_URL, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
        .values(
            channel.getId().toString(),
            channel.getTitle().toString(),
            channel.getRssUrl().toString(),
            channel.getDescription().toString(),
            channel.getImageUrl().toString()
        )
        .onDuplicateKeyUpdate()
        // This is a weird solution to pass the test.
        // Basically, the rss url you try to save already exists so just update all fields,
        // except the id. Then you try to get by channel id and the id doesn't exists.
        .set(CHANNEL.ID, channel.getId().toString())
        .set(CHANNEL.TITLE, channel.getTitle().toString())
        .set(CHANNEL.RSS_URL, channel.getRssUrl().toString())
        .set(CHANNEL.DESCRIPTION, channel.getDescription().toString())
        .set(CHANNEL.IMG_URL, channel.getImageUrl().toString())
        .execute();
  }

  private static void saveChannelCategories(Configuration configuration, Channel channel) {
    var step = DSL.using(configuration)
        .insertInto(CHANNEL_CATEGORIES,
            CHANNEL_CATEGORIES.CHANNEL_ID,
            CHANNEL_CATEGORIES.CATEGORY_ID);

    String channelId = channel.getId().toString();

    for (CategoryId categoryId : channel.getCategories()) {
      step.values(channelId, categoryId.toString());
    }

    step.onDuplicateKeyIgnore().execute();
  }

  /**
   * Create a channel from a {@link Record5}.
   *
   * <p>The record must have the following fields: ID, TITLE, RSS_URL, DESCRIPTION and IMG_URL.
   */
  private Channel createChannelFromResult(Record5<String, String, String, String, String> result) {
    ChannelId channelId = ChannelId.fromString(result.getValue(CHANNEL.ID));
    ChannelTitle title = new ChannelTitle(result.getValue(CHANNEL.TITLE));
    RssUrl rssUrl = new RssUrl(result.getValue(CHANNEL.RSS_URL));
    ChannelDescription description = new ChannelDescription(result.getValue(CHANNEL.DESCRIPTION));
    ImageUrl imageUrl = new ImageUrl(result.get(CHANNEL.IMG_URL));

    Collection<CategoryId> categories = getCategoriesFromChannel(channelId);

    return new Channel(channelId, title, rssUrl, description, imageUrl, categories);
  }

  private Collection<CategoryId> getCategoriesFromChannel(ChannelId channelId) {
    var result = dsl.select(CATEGORY.ID)
        .from(CATEGORY)
        .join(CHANNEL_CATEGORIES).on(CATEGORY.ID.eq(CHANNEL_CATEGORIES.CATEGORY_ID))
        .where(CHANNEL_CATEGORIES.CHANNEL_ID.eq(channelId.toString()))
        .fetch();
    return result.stream()
        .map(record -> CategoryId.fromString(record.value1()))
        .collect(Collectors.toSet());
  }
}
