package dev.team.readtoday.server.customlist.domain;

import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a list that can be created by the user to store publications.
 */
public class CustomList {

  /**
   * Represents the id (UUID) of a custom list.
   */
  private final CustomListId id;
  /**
   * Represents the title or name given by his owner.
   */
  private final CustomListTitle title;
  /**
   * Represents the id of the user creator (owner) of the custom list.
   */
  private final UserId userId;
  /**
   * Represents a collection of publication ids that belongs to the custom list.
   */
  private final Collection<PublicationId> publications;

  public CustomList(CustomListId id,
      CustomListTitle title, UserId userId,
      Collection<PublicationId> publications) {
    this.id = id;
    this.title = title;
    this.userId = userId;
    this.publications = publications;
  }

  public CustomListId getId() {
    return id;
  }

  public CustomListTitle getTitle() {
    return title;
  }

  public UserId getUserId() {
    return userId;
  }

  public Collection<PublicationId> getPublications() {
    return publications;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CustomList that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId()) && Objects
        .equals(getTitle(), that.getTitle()) && Objects
        .equals(getUserId(), that.getUserId()) && Objects
        .equals(getPublications(), that.getPublications());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getUserId(), getPublications());
  }
}
