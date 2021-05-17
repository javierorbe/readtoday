package dev.team.readtoday.client.usecase.customlist.search.event;

import java.util.Collection;
import dev.team.readtoday.client.usecase.customlist.search.messages.CustomListResponse;

public class SearchUserCustomListsSuccefulEvent {

  private Collection<CustomListResponse> customList;

  public SearchUserCustomListsSuccefulEvent(Collection<CustomListResponse> customList) {
    this.customList = customList;
  }

  public Collection<CustomListResponse> getLists() {
    return this.customList;
  }
}
