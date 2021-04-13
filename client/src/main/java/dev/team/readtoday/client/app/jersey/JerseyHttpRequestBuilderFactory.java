package dev.team.readtoday.client.app.jersey;

import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.tomlj.TomlTable;

public final class JerseyHttpRequestBuilderFactory implements HttpRequestBuilderFactory {

  private final WebTarget baseTarget;
  private final AuthTokenSupplier authTokenSupplier;

  public JerseyHttpRequestBuilderFactory(TomlTable config, AuthTokenSupplier authTokenSupplier) {
    baseTarget = buildServerBaseTarget(config);
    this.authTokenSupplier = authTokenSupplier;
  }

  private static WebTarget buildServerBaseTarget(TomlTable config) {
    String serverBaseUri = config.getString("server");
    Client client = ClientBuilder.newClient();
    return client.target(serverBaseUri);
  }

  @Override
  public HttpRequestBuilder build(String path) {
    return new JerseyHttpRequestBuilder(baseTarget, path);
  }

  @Override
  public HttpRequestBuilder buildWithAuth(String path) {
    return new AuthorizedJerseyHttpRequestBuilder(authTokenSupplier, baseTarget, path);
  }
}
