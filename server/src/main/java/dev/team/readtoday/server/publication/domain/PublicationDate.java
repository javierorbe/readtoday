package dev.team.readtoday.server.publication.domain;

import java.time.OffsetDateTime;

public final class PublicationDate {

  private final OffsetDateTime dateTime;

  public PublicationDate(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    PublicationDate date = (PublicationDate) o;
    return dateTime.equals(date.dateTime);
  }

  @Override
  public int hashCode() {
    return dateTime.hashCode();
  }

  @Override
  public String toString() {
    return dateTime.toString();
  }
}
