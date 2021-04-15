package dev.team.readtoday.server;

import com.auth0.jwt.algorithms.Algorithm;
import com.rometools.rome.io.SyndFeedInput;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import dev.team.readtoday.server.user.infrastructure.oauth.GoogleProfileFetcher;
import java.net.URI;
import java.security.SecureRandom;
import org.jooq.DSLContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tomlj.TomlTable;

final class AppContext extends AnnotationConfigApplicationContext {

  private static final String APP_PACKAGE = "dev.team.readtoday.server";
  private static final int SIGNING_SECRET_KEY_SIZE = 64;

  private final JooqConnectionBuilder jooq;

  AppContext(TomlTable config) {
    ProfileFetcher profileFetcher = buildGoogleProfileFetcher(config);
    JwtTokenManager jwtTokenManager = new JwtTokenManager(buildJwtSigningAlgorithm());

    HikariConfig hikariConfig = new HikariConfig("/datasource.properties");
    jooq = new JooqConnectionBuilder(hikariConfig);

    registerBean(SyndFeedInput.class, SyndFeedInput::new);
    registerBean(ProfileFetcher.class, () -> profileFetcher);
    registerBean(JwtTokenManager.class, () -> jwtTokenManager);
    registerBean(DSLContext.class, jooq::getContext);
    scan(APP_PACKAGE);
    refresh();
  }

  @Override
  public void close() {
    jooq.close();
    super.close();
  }

  private static ProfileFetcher buildGoogleProfileFetcher(TomlTable config) {
    String googleClientId = config.getString("oauth.client_id");
    String googleClientSecret = config.getString("oauth.client_secret");
    URI googleOauthRedirect = URI.create(config.getString("oauth.redirect_uri"));
    return new GoogleProfileFetcher(googleClientId, googleClientSecret, googleOauthRedirect);
  }

  private static Algorithm buildJwtSigningAlgorithm() {
    SecureRandom random = new SecureRandom();
    byte[] secret = new byte[SIGNING_SECRET_KEY_SIZE];
    random.nextBytes(secret);
    return Algorithm.HMAC256(secret);
  }
}
