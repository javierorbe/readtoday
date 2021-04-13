package dev.team.readtoday.server.channel.application;

import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.CategoryDoesNotExist;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;
import java.util.Optional;

@Service
public final class SearchChannelsByCategory {

  private final ChannelRepository repository;
  private final SearchCategoryByName searchCategoryByName;

  public SearchChannelsByCategory(ChannelRepository repository,
                                  SearchCategoryByName searchCategoryByName) {
    this.repository = repository;
    this.searchCategoryByName = searchCategoryByName;
  }

  public Collection<Channel> search(CategoryName categoryName) throws CategoryDoesNotExist {
    Optional<Category> optCategory = searchCategoryByName.search(categoryName);
    Category category = optCategory.orElseThrow(() -> new CategoryDoesNotExist(categoryName));
    return repository.getByCategory(category.getId());
  }
}
