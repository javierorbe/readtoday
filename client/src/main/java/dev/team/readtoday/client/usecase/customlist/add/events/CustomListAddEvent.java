package dev.team.readtoday.client.usecase.customlist.add.events;

import dev.team.readtoday.client.usecase.customlist.add.messages.CustomListAddRequest;

public class CustomListAddEvent {

  private final CustomListAddRequest request;

  public CustomListAddEvent(CustomListAddRequest request) {
    this.request = request;
  }

  public CustomListAddRequest getRequest() {
    return this.request;
  }
}
