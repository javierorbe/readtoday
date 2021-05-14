package dev.team.readtoday.server.customlist.domain;

import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomListRepository {

  /**
   * Create or update a custom list.
   *
   * @param customList to create or update.
   */
  void save(CustomList customList);

  /**
   * Add a publication to the custom list.
   *
   * @param customListId  id of the custom list.
   * @param publicationId id of the publication that want to be added to the custom list.
   */
  void addPublication(CustomListId customListId, PublicationId publicationId);

  Optional<CustomList> getFromId(CustomListId customListId);

  List<PublicationId> getPublications(CustomListId customListId);

  Collection<CustomList> getListsFromUser(UserId userId);
}
