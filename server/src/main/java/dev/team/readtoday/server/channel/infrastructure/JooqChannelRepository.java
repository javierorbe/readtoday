package dev.team.readtoday.server.channel.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryId;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.channelcategory.infrastructure.JooqChannelCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Result;

public class JooqChannelRepository implements
    dev.team.readtoday.server.channel.domain.ChannelRepository {

  private final DSLContext dsl;

  public JooqChannelRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Channel channel) {
    dsl.insertInto(CHANNEL, CHANNEL.ID, CHANNEL.TITLE, CHANNEL.LINK, CHANNEL.IMG_URL)
        .values(
            channel.getId().toString(),
            channel.getTitle().toString(),
            channel.getRssUrl().toString(),
            channel.getImageUrl().toString()
        ).execute();

    JooqChannelCategoryRepository channelCategoryRep = new JooqChannelCategoryRepository(dsl);
    // Create rows in (many-to-many) channel-category.
    channel.getCategories().forEach(
        category -> channelCategoryRep.save(channel.getId(), category.getId()));
  }

  /**
   * Returns a list of channels that have the category param in common.
   *
   * @param categoryId Category id
   * @return List of channels
   */
  @Override
  public Optional<List<Channel>> getAllByCategoryId(CategoryId categoryId) {

    // Get channels without Categories field
    Result<Record5<String, String, String, String, String>> resultChannels =
        dsl.select(CHANNEL.ID, CHANNEL.TITLE, CHANNEL.LINK, CHANNEL.DESCRIPTION, CHANNEL.IMG_URL)
            .from(CHANNEL)
            .join(CHANNEL_CATEGORIES).on(CHANNEL.ID.eq(CHANNEL_CATEGORIES.CHANNEL_ID))
            .where(CATEGORY.ID.eq(categoryId.toString()))
            .fetch();

    // Create return channels
    List<Channel> channels = new ArrayList<>();

    // Go over all result Channels
    for (Record5<String, String, String, String, String> resultChannel : resultChannels) {
      Channel channel = createChannelFromResult(resultChannel);
      channels.add(channel);
    }

    return Optional.of(channels);
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
    RssUrl rssUrl = new RssUrl(result.getValue(CHANNEL.LINK));
    ChannelDescription description = new ChannelDescription(result.getValue(CHANNEL.DESCRIPTION));
    ImageUrl imageUrl = new ImageUrl(result.get(CHANNEL.IMG_URL));

    JooqChannelCategoryRepository channelCategoryRep = new JooqChannelCategoryRepository(dsl);
    List<Category> categories = channelCategoryRep.getCategoriesFromChannelId(id);

    return new Channel(id, title, rssUrl, description, imageUrl, categories);
  }
}
