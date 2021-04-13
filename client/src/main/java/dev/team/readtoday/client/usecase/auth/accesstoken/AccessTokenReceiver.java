package dev.team.readtoday.client.usecase.auth.accesstoken;

import dev.team.readtoday.client.usecase.auth.AuthInfoProvider;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.greenrobot.eventbus.EventBus;
import org.tomlj.TomlTable;

public final class AccessTokenReceiver implements AutoCloseable {

  private final URI baseUri;
  private final ResourceConfig resourceConfig;
  private HttpServer server;

  public AccessTokenReceiver(TomlTable config,
                             EventBus eventBus,
                             AuthInfoProvider authInfoProvider) {
    baseUri = URI.create(config.getString("oauth.redirect_uri"));
    resourceConfig = new AccessTokenReceiverConfig(eventBus, authInfoProvider);
    start();
  }

  public void start() {
    server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
  }

  @Override
  public void close() {
    if (server.isStarted()) {
      server.shutdownNow();
    }
  }
}
