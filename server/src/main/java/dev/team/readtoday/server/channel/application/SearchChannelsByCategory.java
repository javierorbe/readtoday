package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public final class SearchChannelsByCategory {

  private final ChannelRepository repository;
  private final SearchCategoryByName searchCategoryByName;

  public SearchChannelsByCategory(ChannelRepository repository,
                                  SearchCategoryByName searchCategoryByName) {
    this.repository = repository;
    this.searchCategoryByName = searchCategoryByName;
  }

  public Collection<Channel> search(CategoryName categoryName) {
    Optional<Category> category = searchCategoryByName.search(categoryName);
    if (category.isPresent()) {
      return repository.getByCategory(category.get().getId());
    }
    return Set.of();
  }
}
