package dev.team.readtoday.server.shared.infrastructure.controller;

import static org.mockito.Mockito.mock;

import com.auth0.jwt.algorithms.Algorithm;
import com.rometools.rome.io.SyndFeedInput;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.JwtTokenManager;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import java.security.SecureRandom;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class AcceptanceTestAppContext extends AnnotationConfigApplicationContext {

  private static final String SERVER_PACKAGE = "dev.team.readtoday.server";
  private static final int SIGNING_SECRET_KEY_SIZE = 64;

  private final JooqConnectionBuilder jooq;
  private final JwtTokenManager jwtTokenManager;

  public AcceptanceTestAppContext(ProfileFetcher profileFetcher) {
    jwtTokenManager = new JwtTokenManager(buildJwtSigningAlgorithm());

    HikariConfig hikariConfig = new HikariConfig("/datasource-test.properties");
    jooq = new JooqConnectionBuilder(hikariConfig);

    registerBean(SyndFeedInput.class, SyndFeedInput::new);
    registerBean(ProfileFetcher.class, () -> profileFetcher);
    registerBean(JwtTokenManager.class, () -> jwtTokenManager);
    registerBean(DSLContext.class, jooq::getContext);
    scan(SERVER_PACKAGE);
    refresh();
  }

  public AcceptanceTestAppContext() {
    this(mock(ProfileFetcher.class));
  }

  public JwtTokenManager getJwtTokenManager() {
    return jwtTokenManager;
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
}
