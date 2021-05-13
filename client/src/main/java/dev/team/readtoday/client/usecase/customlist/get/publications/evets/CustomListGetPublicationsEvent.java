package dev.team.readtoday.client.usecase.customlist.get.publications.evets;

import dev.team.readtoday.client.usecase.customlist.get.publications.messages.CustomListGetPublicationsRequest;

public class CustomListGetPublicationsEvent {

  private final CustomListGetPublicationsRequest request;

  public CustomListGetPublicationsEvent(CustomListGetPublicationsRequest request) {
    this.request = request;
  }

  public CustomListGetPublicationsRequest getRequest() {
    return request;
  }
}
