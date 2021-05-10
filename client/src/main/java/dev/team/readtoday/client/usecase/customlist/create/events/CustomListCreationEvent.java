package dev.team.readtoday.client.usecase.customlist.create.events;

import dev.team.readtoday.client.usecase.customlist.create.messages.CustomListCreationRequest;

public final class CustomListCreationEvent {

  private final CustomListCreationRequest request;

  public CustomListCreationEvent(CustomListCreationRequest request) {
    this.request = request;
  }

  public CustomListCreationRequest getRequest() {
    return request;
  }
}
