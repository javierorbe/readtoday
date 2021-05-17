package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import java.util.Collection;

public class CustomListResponse {

  private String id;
  private String title;
  private String userId;
  private Collection<String> publicationsId;

  public CustomListResponse(String id, String title, String userId,
      Collection<String> publicationsId) {
    this.id = id;
    this.title = title;
    this.userId = userId;
    this.publicationsId = publicationsId;
  }

  public String getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getUserId() {
    return this.userId;
  }

  public Collection<String> getPublicationId() {
    return this.publicationsId;
  }
}
