package dev.team.readtoday.client.app.jersey;

import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

final class JerseyHttpRequestBuilder implements HttpRequestBuilder {

  private final WebTarget target;

  JerseyHttpRequestBuilder(WebTarget baseTarget, String path) {
    target = baseTarget.path(path);
  }

  private JerseyHttpRequestBuilder(WebTarget target) {
    this.target = target;
  }

  @Override
  public HttpRequestBuilder withParam(String name, Object... values) {
    WebTarget newTarget = target.queryParam(name, values);
    return new JerseyHttpRequestBuilder(newTarget);
  }

  @Override
  public JerseyHttpResponse get() {
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return new JerseyHttpResponse(response);
  }

  @Override
  public JerseyHttpResponse post(Object entity) {
    Response response = target.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    return new JerseyHttpResponse(response);
  }

  @Override
  public HttpResponse put(String document, Object entity) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public HttpResponse put(Object entity) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public HttpResponse delete(String document) {
    throw new UnsupportedOperationException("Not implemented.");
  }
}
