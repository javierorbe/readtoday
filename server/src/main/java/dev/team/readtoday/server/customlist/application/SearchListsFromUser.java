package dev.team.readtoday.server.customlist.application;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;

@Service
public class SearchListsFromUser {

  private final CustomListRepository repository;

  public SearchListsFromUser(CustomListRepository repository) {
    this.repository = repository;
  }

  public Collection<CustomList> search(UserId userId) {

    return repository.getListsFromUser(userId);

  }
}

