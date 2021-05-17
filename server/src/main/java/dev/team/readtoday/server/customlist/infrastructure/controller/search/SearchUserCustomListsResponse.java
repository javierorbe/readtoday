package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import java.util.Collection;

public class SearchUserCustomListsResponse {

  private Collection<CustomListResponse> customLists;

  public SearchUserCustomListsResponse(Collection<CustomListResponse> customLists) {
    this.customLists = customLists;
  }

  public Collection<CustomListResponse> getLists() {
    return this.customLists;
  }
}
