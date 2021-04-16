package dev.team.readtoday.server.shared.domain;

import java.util.Objects;

public class IgnoreCaseStringValueObject {

  private final String value;

  protected IgnoreCaseStringValueObject(String value) {
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    IgnoreCaseStringValueObject other = (IgnoreCaseStringValueObject) o;
    return value.equalsIgnoreCase(other.value);
  }

  @Override
  public final int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value;
  }
}
