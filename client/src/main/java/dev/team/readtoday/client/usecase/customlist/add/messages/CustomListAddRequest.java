package dev.team.readtoday.client.usecase.customlist.add.messages;

public class CustomListAddRequest {

  private final String publicationId;

  public CustomListAddRequest(String customListId, String publicationId) {
    this.publicationId = publicationId;
  }

  public String getPublicationId() {
    return this.publicationId;
  }
}
