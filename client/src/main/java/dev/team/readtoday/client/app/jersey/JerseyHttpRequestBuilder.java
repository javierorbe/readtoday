package dev.team.readtoday.client.app.jersey;

import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public final class JerseyHttpRequestBuilder implements HttpRequestBuilder {

  private final WebTarget target;

  public JerseyHttpRequestBuilder(WebTarget baseTarget, String path) {
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
  public JerseyResponse get() {
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return new JerseyResponse(response);
  }

  @Override
  public JerseyResponse post(Object entity) {
    Response response = target.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    return new JerseyResponse(response);
  }
}
