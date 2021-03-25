package dev.team.readtoday.server.channelcategory.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryId;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.channelcategory.domain.ChannelCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

public class JooqChannelCategoryRepository implements ChannelCategoryRepository {

  private final DSLContext dsl;

  public JooqChannelCategoryRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(ChannelId channelId, CategoryId categoryId) {
    dsl.insertInto(CHANNEL_CATEGORIES,
        CHANNEL_CATEGORIES.CHANNEL_ID,
        CHANNEL_CATEGORIES.CATEGORY_ID)
        .values(channelId.toString(), categoryId.toString())
        .execute();
  }

  /**
   * Returns categories of a channel.
   *
   * @param channelId Channel id
   * @return List of categories
   */
  @Override
  public List<Category> getCategoriesFromChannelId(ChannelId channelId) {
    // Get categories from a channel
    Result<Record2<String, String>> resultCategories = dsl.select(CATEGORY.ID, CATEGORY.NAME)
        .from(CATEGORY)
        .join(CHANNEL_CATEGORIES).on(CATEGORY.ID.eq(CHANNEL_CATEGORIES.CATEGORY_ID))
        .where(CATEGORY.ID.eq(channelId.toString()))
        .fetch();

    List<Category> categories = new ArrayList<>();

    for (Record2<String, String> resultCategory : resultCategories) {
      CategoryId id = CategoryId.fromString(resultCategory.getValue(CATEGORY.ID));
      CategoryName name = new CategoryName(resultCategory.getValue(CATEGORY.NAME));
      // Add a category of a channel
      categories.add(new Category(id, name));
    }

    return categories;
  }
}
