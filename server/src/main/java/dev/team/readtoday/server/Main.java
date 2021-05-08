package dev.team.readtoday.server;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomlj.TomlTable;

public enum Main {
  ;

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    TomlTable config = ConfigurationLoader.load();
    Dotenv dotenv = loadDotenv();
    AppContext context = new AppContext(config, dotenv);

    ResourceConfig jerseyConfig = new JerseyConfig(context);
    URI baseUri = buildBaseUri(config, dotenv);
    HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, jerseyConfig, false);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (server.isStarted()) {
        server.shutdownNow();
        context.close();
      }
    }));

    try {
      server.start();
      LOGGER.info("Server started at {}", baseUri);
    } catch (IOException e) {
      LOGGER.error("Could not start the HTTP server.", e);
    }
  }

  private static URI buildBaseUri(TomlTable config, Dotenv dotenv) {
    String uriTemplate = config.getString("server_uri");
    String serverHost = dotenv.get("READTODAY_SERVER_HOST");
    String serverPort = dotenv.get("READTODAY_SERVER_PORT");
    return URI.create(String.format(uriTemplate, serverHost, serverPort));
  }

  private static Dotenv loadDotenv() {
    File file = new File("./.env.local");
    return Dotenv.configure()
        .filename(file.exists() ? ".env.local" : ".env")
        .ignoreIfMissing()
        .load();
  }
}
