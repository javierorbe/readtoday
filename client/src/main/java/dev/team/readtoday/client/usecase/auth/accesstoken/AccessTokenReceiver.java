package dev.team.readtoday.client.usecase.auth.accesstoken;

import com.google.common.eventbus.EventBus;
import dev.team.readtoday.client.usecase.auth.AuthInfoProvider;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class AccessTokenReceiver implements AutoCloseable {

  private final URI baseUri;
  private final EventBus eventBus;
  private final AuthInfoProvider authInfoProvider;

  private ResourceConfig config;

  private HttpServer server;

  public AccessTokenReceiver(URI baseUri, EventBus eventBus, AuthInfoProvider authInfoProvider) {
    this.baseUri = baseUri;
    this.eventBus = eventBus;
    this.authInfoProvider = authInfoProvider;
    config = new AccessTokenReceiverConfig(eventBus, authInfoProvider);
    start();
  }

  public void start() {
    server = GrizzlyHttpServerFactory
        .createHttpServer(baseUri, config);
  }

  @Override
  public void close() {
    if (server.isStarted()) {
      server.shutdownNow();
    }
  }
}
