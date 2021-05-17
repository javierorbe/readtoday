package dev.team.readtoday.client.usecase.customlist.search.messages;

import java.util.Collection;

public class SearchUserCustomListsResponse {

  private Collection<CustomListResponse> customLists;

  public SearchUserCustomListsResponse(Collection<CustomListResponse> customLists) {
    this.customLists = customLists;
  }

  public Collection<CustomListResponse> getList() {
    return this.customLists;
  }
}
