package dev.team.readtoday.server;

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
    AppContext context = new AppContext(config);

    ResourceConfig jerseyConfig = new JerseyConfig(context);
    URI baseUri = URI.create(config.getString("server"));
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
}
