package dev.team.readtoday.server.customlist.application;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.shared.domain.CustomListId;
import java.util.Optional;

public class SearchCustomList {

  private final CustomListRepository customListRepository;

  public SearchCustomList(CustomListRepository customListRepository) {
    this.customListRepository = customListRepository;
  }

  public Optional<CustomList> search(CustomListId customListId) throws Exception  {
    if(customListRepository.getFromId(customListId).isPresent()) {
      return customListRepository.getFromId(customListId);
    }
    throw new Exception("Non existing list");
  }
}
