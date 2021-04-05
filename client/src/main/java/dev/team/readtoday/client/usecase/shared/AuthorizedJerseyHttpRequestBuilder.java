package dev.team.readtoday.client.usecase.shared;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Objects;

public final class AuthorizedJerseyHttpRequestBuilder implements HttpRequestBuilder {

  private final AuthTokenSupplier authTokenSupplier;
  private final WebTarget target;

  public AuthorizedJerseyHttpRequestBuilder(AuthTokenSupplier authTokenSupplier,
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
  public JerseyResponse get() {
    String authToken = Objects.requireNonNull(authTokenSupplier.getAuthToken());
    Response response = target.request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        .get();
    return new JerseyResponse(response);
  }

  @Override
  public JerseyResponse post(Object entity) {
    String authToken = Objects.requireNonNull(authTokenSupplier.getAuthToken());
    Response response = target.request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    return new JerseyResponse(response);
  }
}
