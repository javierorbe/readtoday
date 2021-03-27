package dev.team.readtoday.server.channel.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.Url;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Result;

public final class JooqChannelRepository implements ChannelRepository {

  private final DSLContext dsl;

  public JooqChannelRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Channel channel) {
    dsl.insertInto(CHANNEL, CHANNEL.ID, CHANNEL.TITLE, CHANNEL.LINK, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
        .values(
            channel.getId().toString(),
            channel.getTitle().toString(),
            channel.getRssUrl().toString(),
            channel.getDescription().toString(),
            channel.getImageUrl().toString()
        ).execute();

    // TODO: Decide if categories should be created at the same time a channel is created.
    //       Otherwise category creation should be done before channel creation.

    // Create rows in (many-to-many) channel-category.
    channel.getCategories()
        .forEach(category -> bindWithCategories(channel.getId(), category.getName()));
  }

  /**
   * Returns a list of channels that have the category param in common.
   *
   * @param categoryName Category name
   * @return List of channels
   */
  @Override
  public Optional<List<Channel>> getAllByCategoryName(CategoryName categoryName) {

    // Get channels without Categories field
    Result<Record5<String, String, String, String, String>> resultChannels =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.LINK, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .join(CHANNEL_CATEGORIES).on(CHANNEL.ID.eq(CHANNEL_CATEGORIES.CHANNEL_ID))
            .where(CATEGORY.CATEGORY_NAME.eq(categoryName.toString()))
            .fetch();

    // Create return channels
    List<Channel> channels = new ArrayList<>();

    // Go over all result Channels
    for (var resultChannel : resultChannels) {
      Channel channel = createChannelFromResult(resultChannel);
      channels.add(channel);
    }

    return Optional.of(channels);
  }

  public Optional<Channel> getFromId(ChannelId id) {
    Record5<String, String, String, String, String> channelResult =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.LINK, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .where(CHANNEL.ID.eq(id.toString()))
            .fetchOne();

    if (channelResult == null) {
      return Optional.empty();
    }

    Channel channel = createChannelFromResult(channelResult);

    return Optional.of(channel);

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
      Record5<String, String, String, String, String> result) {

    ChannelId id = ChannelId.fromString(result.getValue(CHANNEL.ID));
    ChannelTitle title = new ChannelTitle(result.getValue(CHANNEL.TITLE));
    Url rssUrl = new Url(result.getValue(CHANNEL.LINK));
    ChannelDescription description = new ChannelDescription(result.getValue(CHANNEL.DESCRIPTION));
    Url imageUrl = new Url(result.get(CHANNEL.IMG_URL));

    List<Category> categories = getCategoriesFromChannelId(id);
    return new Channel(id, title, rssUrl, description, imageUrl, categories);
  }

  /**
   * Returns categories of a channel.
   *
   * @param channelId Channel id
   * @return List of categories
   */

  public List<Category> getCategoriesFromChannelId(ChannelId channelId) {
    // Get categories from a channel
    Result<Record1<String>> resultCategories = dsl.select(CATEGORY.CATEGORY_NAME)
        .from(CATEGORY)
        .join(CHANNEL_CATEGORIES).on(CATEGORY.CATEGORY_NAME.eq(CHANNEL_CATEGORIES.CATEGORY_NAME))
        .where(CATEGORY.CATEGORY_NAME.eq(channelId.toString()))
        .fetch();

    List<Category> categories = new ArrayList<>();

    for (var resultCategory : resultCategories) {
      CategoryName name = new CategoryName(resultCategory.getValue(CATEGORY.CATEGORY_NAME));
      // Add a category of a channel
      categories.add(new Category(name));
    }

    return categories;
  }

  private void bindWithCategories(ChannelId channelId, CategoryName categoryName) {
    dsl.insertInto(CHANNEL_CATEGORIES,
        CHANNEL_CATEGORIES.CHANNEL_ID,
        CHANNEL_CATEGORIES.CATEGORY_NAME)
        .values(channelId.toString(), categoryName.toString())
        .execute();
  }

}
