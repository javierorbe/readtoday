package dev.team.readtoday.client.app.jersey;

import dev.team.readtoday.client.usecase.shared.HttpResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response.StatusType;

final class JerseyHttpResponse implements HttpResponse {

  private final Response response;

  JerseyHttpResponse(Response response) {
    this.response = response;
  }

  @Override
  public boolean isStatusOk() {
    return isStatus(Status.OK);
  }

  @Override
  public boolean isStatusCreated() {
    return isStatus(Status.CREATED);
  }

  @Override
  public boolean isStatusNoContent() {
    return isStatus(Status.NO_CONTENT);
  }

  private boolean isStatus(StatusType status) {
    return response.getStatus() == status.getStatusCode();
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
