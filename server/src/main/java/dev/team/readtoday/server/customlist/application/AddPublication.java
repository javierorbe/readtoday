package dev.team.readtoday.server.customlist.application;

import dev.team.readtoday.server.customlist.domain.CustomListRepository;

import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;


public class AddPublication {

  private final CustomListRepository customListRepository;

  public AddPublication(CustomListRepository customListRepository) {
    this.customListRepository = customListRepository;
  }

  public void add(CustomListId customListId, PublicationId publicationId) {
    customListRepository.addPublication(customListId, publicationId);
  }
}
