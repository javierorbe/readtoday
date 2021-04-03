package dev.team.readtoday.server.user.infrastructure.controller;

import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import dev.team.readtoday.server.user.application.SignUpUser;
import dev.team.readtoday.server.user.domain.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class SignUpTestingJerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  SignUpTestingJerseyConfig(JwtTokenManager jwtTokenManager,
                            UserRepository userRepository,
                            ProfileFetcher profileFetcher) {

    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(jwtTokenManager).to(JwtTokenManager.class);
        bind(new SignUpUser(profileFetcher, userRepository)).to(SignUpUser.class);
      }
    });
  }
}
