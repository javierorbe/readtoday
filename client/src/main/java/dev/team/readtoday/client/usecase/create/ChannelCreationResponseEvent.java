package dev.team.readtoday.client.usecase.create;

import jakarta.ws.rs.core.Response;

public final class ChannelCreationResponseEvent {

  private final Response response;

  public ChannelCreationResponseEvent(Response response) {
    this.response = response;
  }

  public Response getResponse() {
    return response;
  }
}
