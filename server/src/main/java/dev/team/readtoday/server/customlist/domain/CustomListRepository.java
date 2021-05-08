package dev.team.readtoday.server.customlist.domain;

public interface CustomListRepository {

  /**
   * Create or update a custom list in database.
   *
   * @param customList to create or update in database.
   */
  void save(CustomList customList);
}
