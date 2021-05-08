package dev.team.readtoday.server.customlist.domain;

import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.Objects;

public class CustomList {
  private String listName;
  private UserId userId;
  private final Collection<PublicationId> publicationlist;

  public CustomList(String listName, UserId userId,
      Collection<PublicationId> publicationlist) {
    this.listName = listName;
    this.userId = userId;
    this.publicationlist = publicationlist;
  }

  public String getListName() {
    return listName;
  }

  public UserId getUserId() {
    return userId;
  }

  public Collection<PublicationId> getPublicationlist() {
    return publicationlist;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public void setUserId(UserId userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomList that = (CustomList) o;
    return Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}
