package dev.team.readtoday.client.app.jersey;

import dev.team.readtoday.client.usecase.shared.HttpResponse;
import jakarta.ws.rs.core.Response;

public final class JerseyResponse implements HttpResponse {

  private final Response response;

  JerseyResponse(Response response) {
    this.response = response;
  }

  @Override
  public boolean isStatusOk() {
    return response.getStatus() == Response.Status.OK.getStatusCode();
  }

  @Override
  public boolean isStatusCreated() {
    return response.getStatus() == Response.Status.CREATED.getStatusCode();
  }

  @Override
  public String getStatusReason() {
    return response.getStatusInfo().getReasonPhrase();
  }

  @Override
  public <T> T getEntity(Class<T> clazz) {
    return response.readEntity(clazz);
  }
}
