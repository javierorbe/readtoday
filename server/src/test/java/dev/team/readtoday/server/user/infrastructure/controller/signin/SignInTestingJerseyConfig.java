package dev.team.readtoday.server.user.infrastructure.controller.signin;

import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import dev.team.readtoday.server.user.application.SignInUser;
import dev.team.readtoday.server.user.domain.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class SignInTestingJerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  SignInTestingJerseyConfig(JwtTokenManager jwtTokenManager,
                            UserRepository userRepository,
                            ProfileFetcher profileFetcher) {

    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(jwtTokenManager).to(JwtTokenManager.class);
        bind(new SignInUser(profileFetcher, userRepository)).to(SignInUser.class);
      }
    });
  }
}
