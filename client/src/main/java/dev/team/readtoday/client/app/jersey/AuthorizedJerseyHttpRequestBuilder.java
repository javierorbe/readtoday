package dev.team.readtoday.client.app.jersey;

import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Objects;

final class AuthorizedJerseyHttpRequestBuilder implements HttpRequestBuilder {

  private final AuthTokenSupplier authTokenSupplier;
  private final WebTarget target;

  AuthorizedJerseyHttpRequestBuilder(AuthTokenSupplier authTokenSupplier,
                                     WebTarget baseTarget,
                                     String path) {
    this.authTokenSupplier = authTokenSupplier;
    target = baseTarget.path(path);
  }

  private AuthorizedJerseyHttpRequestBuilder(AuthTokenSupplier authTokenSupplier,
                                             WebTarget target) {
    this.authTokenSupplier = authTokenSupplier;
    this.target = target;
  }

  @Override
  public HttpRequestBuilder withParam(String name, Object... values) {
    WebTarget newTarget = target.queryParam(name, values);
    return new AuthorizedJerseyHttpRequestBuilder(authTokenSupplier, newTarget);
  }

  @Override
  public JerseyHttpResponse get() {
    Response response = withAuthHeader().get();
    return new JerseyHttpResponse(response);
  }

  @Override
  public JerseyHttpResponse post(Object entity) {
    Response response = withAuthHeader().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    return new JerseyHttpResponse(response);
  }

  @Override
  public HttpResponse delete(String document) {
    target.path(document);
    Response response = withAuthHeader().delete();
    return new JerseyHttpResponse(response);
  }

  private Invocation.Builder withAuthHeader() {
    String authToken = Objects.requireNonNull(authTokenSupplier.getAuthToken());
    return target.request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
  }
}
