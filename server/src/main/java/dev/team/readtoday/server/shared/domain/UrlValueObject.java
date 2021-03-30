package dev.team.readtoday.server.shared.domain;

import java.net.URL;
import java.util.Objects;

public class UrlValueObject {

  private final URL value;

  protected UrlValueObject(URL value) {
    this.value = value;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UrlValueObject)) {
      return false;
    }
    UrlValueObject that = (UrlValueObject) o;
    return value.equals(that.value);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public final String toString() {
    return value.toString();
  }
}
