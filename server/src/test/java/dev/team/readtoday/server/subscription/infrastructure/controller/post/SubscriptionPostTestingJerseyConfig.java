package dev.team.readtoday.server.subscription.infrastructure.controller.post;

import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.subscription.application.CreateSubscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class SubscriptionPostTestingJerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  SubscriptionPostTestingJerseyConfig(JwtTokenManager jwtTokenManager,
                                      UserRepository userRepository,
                                      SubscriptionRepository subRepository) {

    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(jwtTokenManager).to(JwtTokenManager.class);

        bind(new SearchUserById(userRepository)).to(SearchUserById.class);
        bind(new CreateSubscription(subRepository)).to(CreateSubscription.class);
      }
    });
  }
}
