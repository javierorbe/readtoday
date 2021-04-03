package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.SearchCategoriesById;
import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.channel.application.SearchChannelsByCategory;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.user.domain.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class TestingJerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  TestingJerseyConfig(JwtTokenManager jwtTokenManager,
      UserRepository userRepository,
      ChannelRepository channelRepository,
      CategoryRepository categoryRepository) {

    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(jwtTokenManager).to(JwtTokenManager.class);

        SearchCategoryByName searchCategoryByName = new SearchCategoryByName(categoryRepository);

        bind(new SearchCategoriesById(categoryRepository)).to(SearchCategoriesById.class);
        bind(new SearchChannelsByCategory(channelRepository, searchCategoryByName)).to(SearchChannelsByCategory.class);
        bind(new SearchCategoryByName(categoryRepository)).to(SearchCategoryByName.class);

        bind(userRepository).to(UserRepository.class);
        bind(channelRepository).to(ChannelRepository.class);
        bind(categoryRepository).to(CategoryRepository.class);
      }
    });
  }
}
