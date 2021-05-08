package dev.team.readtoday.server;

import com.auth0.jwt.algorithms.Algorithm;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import dev.team.readtoday.server.user.infrastructure.oauth.GoogleProfileFetcher;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.security.SecureRandom;
import org.jooq.DSLContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tomlj.TomlTable;

final class AppContext extends AnnotationConfigApplicationContext {

  private static final String APP_PACKAGE = "dev.team.readtoday.server";
  private static final int SIGNING_SECRET_KEY_SIZE = 64;

  private final JooqConnectionBuilder jooq;

  AppContext(TomlTable config, Dotenv dotenv) {
    HikariConfig hikariConfig = buildHikariConfig(config, dotenv);
    jooq = new JooqConnectionBuilder(hikariConfig);

    ProfileFetcher profileFetcher = buildGoogleProfileFetcher(config, dotenv);

    registerBean("jwtSigningAlg",
        Algorithm.class,
        AppContext::buildJwtSigningAlgorithm);
    registerBean(ProfileFetcher.class, () -> profileFetcher);
    registerBean(DSLContext.class, jooq::getContext);
    scan(APP_PACKAGE);
    refresh();
  }

  @Override
  public void close() {
    jooq.close();
    super.close();
  }

  private static ProfileFetcher buildGoogleProfileFetcher(TomlTable config, Dotenv dotenv) {
    String googleClientId = dotenv.get("READTODAY_GOOGLE_CLIENT_ID");
    String googleClientSecret = dotenv.get("READTODAY_GOOGLE_CLIENT_SECRET");
    URI googleOauthRedirect = URI.create(config.getString("oauth_redirect_uri"));
    return new GoogleProfileFetcher(googleClientId, googleClientSecret, googleOauthRedirect);
  }

  private static Algorithm buildJwtSigningAlgorithm() {
    SecureRandom random = new SecureRandom();
    byte[] secret = new byte[SIGNING_SECRET_KEY_SIZE];
    random.nextBytes(secret);
    return Algorithm.HMAC256(secret);
  }

  private static HikariConfig buildHikariConfig(TomlTable config, Dotenv dotenv) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.addDataSourceProperty("cachePrepStmts", true);
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    String jdbcDriver = config.getString("jdbc.driver");
    hikariConfig.setDriverClassName(jdbcDriver);
    hikariConfig.setUsername(dotenv.get("READTODAY_DB_USER"));
    hikariConfig.setPassword(dotenv.get("READTODAY_DB_PASSWORD"));
    String jdbcUrlTemplate = config.getString("jdbc.url");
    String dbHost = dotenv.get("READTODAY_DB_HOST");
    String dbPort = dotenv.get("READTODAY_DB_PORT");
    String database = dotenv.get("READTODAY_DB_DATABASE");
    String jdbcUrl = String.format(jdbcUrlTemplate, dbHost, dbPort, database);
    hikariConfig.setJdbcUrl(jdbcUrl);
    return hikariConfig;
  }
}
