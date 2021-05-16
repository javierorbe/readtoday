package dev.team.readtoday.server.customlist.infrastructure.controller.get;

public class CustomListGetPublicationsRequest {

  private String customListId;

  public String getCustomListId() {
    return this.customListId;
  }

  public void setCustomListId(String customListId) {
    this.customListId = customListId;
  }
}
