package dev.team.readtoday.client.usercase.subscription;

import jakarta.ws.rs.core.Response;

public final class SubscriptionResponseEvent {

  private final Response response;

  public SubscriptionResponseEvent(Response response) {
    this.response = response;
  }

  public Response getResponse() {
    return this.response;
  }
}
