package dev.team.readtoday.client.auth.accesstoken;

import com.google.common.eventbus.EventBus;
import dev.team.readtoday.client.auth.AuthInfoProvider;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public final class AccessTokenReceiver implements AutoCloseable {

  private final HttpServer server;

  public AccessTokenReceiver(URI baseUri, EventBus eventBus, AuthInfoProvider authInfoProvider) {
    server = GrizzlyHttpServerFactory
        .createHttpServer(baseUri, new JerseyConfig(eventBus, authInfoProvider));
  }

  @Override
  public void close() {
    server.shutdownNow();
  }
}
