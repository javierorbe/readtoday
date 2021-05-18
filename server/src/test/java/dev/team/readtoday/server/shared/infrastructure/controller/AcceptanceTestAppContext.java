package dev.team.readtoday.server.shared.infrastructure.controller;

import static org.mockito.Mockito.mock;

import com.auth0.jwt.algorithms.Algorithm;
import com.rometools.rome.io.SyndFeedInput;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.jwt.application.get.GetJwtToken;
import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import java.security.SecureRandom;
import java.time.Clock;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class AcceptanceTestAppContext extends AnnotationConfigApplicationContext {

  private static final String SERVER_PACKAGE = "dev.team.readtoday.server";
  private static final int SIGNING_SECRET_KEY_SIZE = 64;

  private final JooqConnectionBuilder jooq;

  public AcceptanceTestAppContext(ProfileFetcher profileFetcher) {

    HikariConfig hikariConfig = new HikariConfig("/datasource-test.properties");
    jooq = new JooqConnectionBuilder(hikariConfig);

    registerBean("jwtSigningAlg",
        Algorithm.class,
        AcceptanceTestAppContext::buildJwtSigningAlgorithm);
    registerBean(SyndFeedInput.class, SyndFeedInput::new);
    registerBean(ProfileFetcher.class, () -> profileFetcher);
    registerBean(DSLContext.class, jooq::getContext);
    registerBean(Clock.class, Clock::systemDefaultZone);
    scan(SERVER_PACKAGE);
    refresh();
  }

  public AcceptanceTestAppContext() {
    this(mock(ProfileFetcher.class));
  }

  public void clearTables(Table<?>... tables) {
    for (Table<?> table : tables) {
      jooq.getContext().transaction(config -> DSL.using(config).deleteFrom(table).execute());
    }
  }

  @Override
  public void close() {
    jooq.close();
    super.close();
  }

  private static Algorithm buildJwtSigningAlgorithm() {
    SecureRandom random = new SecureRandom();
    byte[] secret = new byte[SIGNING_SECRET_KEY_SIZE];
    random.nextBytes(secret);
    return Algorithm.HMAC256(secret);
  }

  public JwtToken getJwtTokenForUser(String userId) {
    GetJwtToken getJwtToken = getBean(GetJwtToken.class);
    return getJwtToken.apply(UserId.fromString(userId));
  }
}
