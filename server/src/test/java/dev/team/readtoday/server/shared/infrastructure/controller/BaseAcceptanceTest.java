package dev.team.readtoday.server.shared.infrastructure.controller;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.tomlj.TomlTable;

public class BaseAcceptanceTest {

  private HttpServer server;
  private URI serverBaseUri;

  protected final void initServer(ConfigurableApplicationContext context) {
    TomlTable config = TestingConfigurationLoader.load();
    serverBaseUri = URI.create(config.getString("server"));
    ResourceConfig jerseyConfig = new TestingJerseyConfig(context);
    server = GrizzlyHttpServerFactory.createHttpServer(serverBaseUri, jerseyConfig);
  }

  protected final void closeServer() {
    if ((server != null) && server.isStarted()) {
      server.shutdownNow();
    }
  }

  protected final URI getServerBaseUri() {
    return serverBaseUri;
  }
}
