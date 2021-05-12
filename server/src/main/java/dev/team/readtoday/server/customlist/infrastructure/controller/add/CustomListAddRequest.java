package dev.team.readtoday.server.customlist.infrastructure.controller.add;

public class CustomListAddRequest {

  private String publicationId;

  public String getPublicationId() {
    return this.publicationId;
  }

  public void setPublicationId(String publicationId) {
    this.publicationId = publicationId;
  }
}
