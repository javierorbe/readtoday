package dev.team.readtoday.server.customlist.application;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.shared.domain.Service;

@Service
public class CreateCustomList {

  private final CustomListRepository repository;

  public CreateCustomList(CustomListRepository repository) {
    this.repository = repository;
  }

  public void save(CustomList customList) {
    repository.save(customList);
  }
}
