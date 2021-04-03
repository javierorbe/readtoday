package dev.team.readtoday.server.shared.infrastructure.controller;

import com.auth0.jwt.algorithms.Algorithm;
import dev.team.readtoday.server.category.application.SearchCategoriesById;
import dev.team.readtoday.server.category.application.SearchCategoryByName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.category.infrastructure.persistence.JooqCategoryRepository;
import dev.team.readtoday.server.channel.application.SearchChannelsByCategory;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import dev.team.readtoday.server.user.application.SignUpUser;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public final class JerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  private static final Algorithm JWT_SIGNING_ALGORITHM = Algorithm.HMAC256("someSecret");

  public JerseyConfig(ProfileFetcher profileFetcher, JooqConnectionBuilder jooq) {
    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(new JwtTokenManager(JWT_SIGNING_ALGORITHM)).to(JwtTokenManager.class);

        UserRepository userRepository = new JooqUserRepository(jooq.getContext());
        ChannelRepository channelRepository = new JooqChannelRepository(jooq.getContext());
        CategoryRepository categoryRepository = new JooqCategoryRepository(jooq.getContext());

        SearchCategoryByName searchCategoryByName = new SearchCategoryByName(categoryRepository);

        bind(new SearchChannelsByCategory(channelRepository, searchCategoryByName)).to(SearchChannelsByCategory.class);
        bind(new SearchCategoriesById(categoryRepository)).to(SearchCategoriesById.class);
        bind(new SearchCategoryByName(categoryRepository)).to(SearchCategoryByName.class);
        bind(new SignUpUser(profileFetcher, userRepository)).to(SignUpUser.class);
      }
    });
  }
}
