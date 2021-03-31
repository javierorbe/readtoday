package dev.team.readtoday.server.category.application;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SearchCategory {

  private final CategoryRepository categoryRepository;

  public SearchCategory(
      CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * Returns a set of categories from channels.
   *
   * @param channels List of channels
   * @return Set of categories
   */
  public Set<Category> getFromChannels(List<Channel> channels) {
    Set<Category> categories = new HashSet<>();

    for (Channel channel : channels) {
      List<CategoryId> categoryIds = channel.getCategoryIds();
      categoryIds.forEach(categoryId -> {
        Optional<Category> category = categoryRepository.getById(categoryId);
        category.ifPresent(categories::add);
      });
    }
    return categories;
  }
}
