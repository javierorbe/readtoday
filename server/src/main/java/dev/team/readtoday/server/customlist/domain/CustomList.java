package dev.team.readtoday.server.customlist.domain;

import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.Objects;

public class CustomList {

  private final CustomListId id;
  private final CustomListTitle title;
  private final UserId userId;
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
