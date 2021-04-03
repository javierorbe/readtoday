package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.category.application.SearchCategoriesById;
import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.channel.application.SearchChannelsByCategory;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class ChannelSearchTestingJerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  ChannelSearchTestingJerseyConfig(JwtTokenManager jwtTokenManager,
                                   ChannelRepository channelRepository,
                                   CategoryRepository categoryRepository) {

    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(jwtTokenManager).to(JwtTokenManager.class);

        SearchCategoryByName searchCategoryByName = new SearchCategoryByName(categoryRepository);
        SearchChannelsByCategory searchChannelsByCat =
            new SearchChannelsByCategory(channelRepository, searchCategoryByName);

        bind(new SearchCategoriesById(categoryRepository)).to(SearchCategoriesById.class);
        bind(searchChannelsByCat).to(SearchChannelsByCategory.class);
      }
    });
  }
}
