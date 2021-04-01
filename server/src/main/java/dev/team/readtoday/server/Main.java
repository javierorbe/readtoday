package dev.team.readtoday.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.shared.infrastructure.controller.JerseyConfig;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import dev.team.readtoday.server.user.infrastructure.oauth.GoogleProfileFetcher;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Main {
  ;

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  public static void main(String[] args) {
    JsonObject config;
    try {
      config = loadConfig();
    } catch (FileNotFoundException | RuntimeException e) {
      LOGGER.error("Config file not found.", e);
      return;
    }

    String googleClientId = config.get("googleClientId").getAsString();
    String googleClientSecret = config.get("googleClientSecret").getAsString();
    URI googleOauthRedirect = URI.create(config.get("googleOauthRedirect").getAsString());
    String baseUri = config.get("baseUri").getAsString();

    ProfileFetcher profileFetcher =
        new GoogleProfileFetcher(googleClientId, googleClientSecret, googleOauthRedirect);
    JooqConnectionBuilder jooq =
        new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));

    ResourceConfig jerseyConfig = new JerseyConfig(profileFetcher, jooq);
    HttpServer server =
        GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), jerseyConfig);

    LOGGER.info("Jersey app started with WADL available at {}.application.wadl", baseUri);

    try {
      System.in.read();
    } catch (IOException e) {
      LOGGER.error("Error waiting for user response.", e);
    }
    server.shutdownNow();
  }

  private static JsonObject loadConfig() throws FileNotFoundException {
    URL fileUrl = Objects.requireNonNull(Main.class.getResource(CONFIG_FILE));
    String file = fileUrl.getFile();
    return GSON.fromJson(new JsonReader(new FileReader(file)), JsonObject.class);
  }
}
