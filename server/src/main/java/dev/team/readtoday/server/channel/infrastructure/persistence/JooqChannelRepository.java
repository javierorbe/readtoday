package dev.team.readtoday.server.channel.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;

import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.jooq.tables.records.CategoryRecord;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.TableField;
import org.jooq.impl.DSL;

public final class JooqChannelRepository implements ChannelRepository {

  private final DSLContext dsl;

  public JooqChannelRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  /**
   * Create or update a channel.
   * Categories of the channel given must be exist (DB) before trying to save the channel.
   *
   * @param channel Channel
   */
  @Override
  public void save(Channel channel) {
    dsl.transaction(configuration -> {

      DSL.using(configuration)
          .insertInto(CHANNEL, CHANNEL.ID, CHANNEL.TITLE,
          CHANNEL.RSS_URL, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
          .values(
              channel.getId().toString(),
              channel.getTitle().toString(),
              channel.getRssUrl().toString(),
              channel.getDescription().toString(),
              channel.getImageUrl().toString()
          ).execute();

      // Create rows in (many-to-many) channel-category.
      channel.getCategoryIds()
          .forEach(categoryId -> bindWithCategories(configuration, channel.getId(), categoryId));
    });
  }


  @Override
  public List<Channel> getAllByCategoryId(CategoryId categoryId) {
    return getAllBy(CATEGORY.ID, categoryId.toString());
  }

  @Override
  public List<Channel> getAllByCategoryName(CategoryName categoryName) {
    return getAllBy(CATEGORY.NAME, categoryName.toString());
  }

  /**
   * Methods just for not duplicate code. Returns a list of channels given a category field
   * and a value for that field.
   * The category field ie. CATEGORY.ID must be unique (still not tested).
   *
   * @param field The category field, ie. CATEGORY.ID, CATEGORY.NAME.
   * @param value the category field value, ie. if CATEGORY.NAME field is passed, you must pass
   *              a value "Entertainment" or "Sports".
   * @return List of channels.
   */
  private List<Channel> getAllBy(TableField<CategoryRecord, String> field, String value) {

    // Get channels without Categories field
    Result<Record5<String, String, String, String, String>> resultChannels =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.RSS_URL, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .join(CHANNEL_CATEGORIES).on(CHANNEL.ID.eq(CHANNEL_CATEGORIES.CHANNEL_ID))
            .join(CATEGORY).on(CHANNEL_CATEGORIES.CATEGORY_ID.eq(CATEGORY.ID))
            .where(field.eq(value))
            .fetch();

    // Create return channels
    List<Channel> channels = new ArrayList<>();

    // Go over all result Channels
    for (var resultChannel : resultChannels) {

      try {
        // Convert the result to channel and link them with their categories.
        Channel channel = createChannelFromResult(resultChannel);
        channels.add(channel);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }

    return channels;
  }


  /**
   * Returns a Channel given a channel id.
   *
   * @param id ChannelId
   * @return Channel
   */
  @Override
  public Optional<Channel> getFromId(ChannelId id) {
    Record5<String, String, String, String, String> channelResult =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.RSS_URL, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .where(CHANNEL.ID.eq(id.toString()))
            .fetchOne();

    if (channelResult == null) {
      return Optional.empty();
    }

    try {
      Channel channel = createChannelFromResult(channelResult);
      return Optional.of(channel);

    } catch (MalformedURLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * Returns a Channel instance from channel's result. It must contains all fields. The Channel
   * returned will contains its categories.
   *
   * @param result Channel result must have the following fields ID, TITLE, LINK, DESCRIPTION and
   *               IMG_URL
   * @return Channel instance
   */
  private Channel createChannelFromResult(
      Record5<String, String, String, String, String> result) throws MalformedURLException {

    ChannelId id = ChannelId.fromString(result.getValue(CHANNEL.ID));
    ChannelTitle title = new ChannelTitle(result.getValue(CHANNEL.TITLE));
    RssUrl rssUrl = new RssUrl(new URL(result.getValue(CHANNEL.RSS_URL)));
    ChannelDescription description = new ChannelDescription(result.getValue(CHANNEL.DESCRIPTION));
    ImageUrl imageUrl = new ImageUrl(new URL(result.get(CHANNEL.IMG_URL)));

    List<CategoryId> categories = getCategoriesFromChannelId(id);
    return new Channel(id, title, rssUrl, description, imageUrl, categories);
  }

  /**
   * Returns categories of a channel.
   *
   * @param channelId Channel id
   * @return List of categories
   */
  private List<CategoryId> getCategoriesFromChannelId(ChannelId channelId) {
    // Get categories from a channel
    Result<Record1<String>> resultCategories = dsl.select(CATEGORY.ID)
        .from(CATEGORY)
        .join(CHANNEL_CATEGORIES).on(CATEGORY.ID.eq(CHANNEL_CATEGORIES.CATEGORY_ID))
        .where(CHANNEL_CATEGORIES.CHANNEL_ID.eq(channelId.toString()))
        .fetch();

    List<CategoryId> categories = new ArrayList<>();

    for (var resultCategory : resultCategories) {
      CategoryId categoryId = CategoryId.fromString(resultCategory.getValue(CATEGORY.ID));
      // Add a category of a channel
      categories.add(categoryId);
    }

    return categories;
  }

  /**
   * Bind a channel with its category. Middle table mxn (Channel - Category)
   *
   * @param channelId ChannelId
   * @param categoryId CategoryId
   */
  private void bindWithCategories(Configuration configuration,
      ChannelId channelId, CategoryId categoryId) {
    DSL.using(configuration)
        .insertInto(CHANNEL_CATEGORIES,
        CHANNEL_CATEGORIES.CHANNEL_ID,
        CHANNEL_CATEGORIES.CATEGORY_ID)
        .values(channelId.toString(), categoryId.toString())
        .execute();
  }
}
