package dev.team.readtoday.server.shared.domain;

import java.net.URL;
import java.util.Objects;

public class URLValueObject {

  private final URL value;

  protected URLValueObject(URL value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof URLValueObject)) {
      return false;
    }
    URLValueObject that = (URLValueObject) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
