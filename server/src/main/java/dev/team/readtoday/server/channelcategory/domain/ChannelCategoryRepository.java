package dev.team.readtoday.server.channelcategory.domain;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryId;
import dev.team.readtoday.server.channel.domain.ChannelId;
import java.util.List;

public interface ChannelCategoryRepository {

  void save(ChannelId channelId, CategoryId categoryId);

  List<Category> getCategoriesFromChannelId(ChannelId channelId);
}
