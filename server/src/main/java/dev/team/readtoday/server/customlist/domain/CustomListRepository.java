package dev.team.readtoday.server.customlist.domain;

import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;

public interface CustomListRepository {

  /**
   * Create or update a custom list in database.
   *
   * @param customList to create or update in database.
   */
  void save(CustomList customList);
  void addPublication(CustomListId customListId, PublicationId publicationId);
}
