package dev.team.readtoday.server.shared.infrastructure.controller;

import com.auth0.jwt.algorithms.Algorithm;
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
    register(new MyAbstractBinder(jooq, profileFetcher));
  }

  private static final class MyAbstractBinder extends AbstractBinder {

    private final JooqConnectionBuilder jooq;
    private final ProfileFetcher profileFetcher;

    private MyAbstractBinder(JooqConnectionBuilder jooq, ProfileFetcher profileFetcher) {
      this.jooq = jooq;
      this.profileFetcher = profileFetcher;
    }

    @Override
    protected void configure() {
      bind(new JwtTokenManager(JWT_SIGNING_ALGORITHM)).to(JwtTokenManager.class);

      UserRepository userRepository = new JooqUserRepository(jooq.getContext());

      bind(new SignUpUser(profileFetcher, userRepository)).to(SignUpUser.class);
    }
  }
}
