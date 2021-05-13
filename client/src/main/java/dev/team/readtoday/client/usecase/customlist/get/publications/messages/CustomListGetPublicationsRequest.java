package dev.team.readtoday.client.usecase.customlist.get.publications.messages;

public class CustomListGetPublicationsRequest {

  private final String customListId;

  public CustomListGetPublicationsRequest(String customListId) {
    this.customListId = customListId;
  }

  public String getCustomListId() {
    return this.customListId;
  }
}
