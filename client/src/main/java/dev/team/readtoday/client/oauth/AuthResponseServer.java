package dev.team.readtoday.client.oauth;

import dev.team.readtoday.client.view.auth.AuthController;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public final class AuthResponseServer implements AutoCloseable {

  private final HttpServer server;

  public AuthResponseServer(URI baseUri,
                            AuthInfoProvider signUpUsernameProv,
                            AuthController authController,
                            JwtTokenReceiver jwtTokenReceiver) {
    server = GrizzlyHttpServerFactory.createHttpServer(baseUri,
        new JerseyConfig(signUpUsernameProv, authController, jwtTokenReceiver));
  }

  @Override
  public void close() {
    server.shutdownNow();
  }
}
