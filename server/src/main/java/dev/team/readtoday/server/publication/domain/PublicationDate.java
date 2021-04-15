package dev.team.readtoday.server.publication.domain;

import java.time.LocalDateTime;

public final class PublicationDate {

  private final LocalDateTime dateTime;

  public PublicationDate(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public static PublicationDate fromString(String date) {
    return new PublicationDate(LocalDateTime.parse(date));
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
}
