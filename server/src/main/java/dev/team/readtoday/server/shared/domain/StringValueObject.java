package dev.team.readtoday.server.shared.domain;

import java.util.regex.Pattern;

public class StringValueObject {

  private final String value;

  protected StringValueObject(String value) {
    this.value = value;
  }

  protected StringValueObject(String value, Pattern pattern) {
    if (!pattern.matcher(value).matches()) {
      throw new IllegalArgumentException("The string does not match the validation pattern: " + pattern);
    }
    this.value = value;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    StringValueObject strValueObject = (StringValueObject) o;
    return value.equals(strValueObject.value);
  }

  @Override
  public final int hashCode() {
    return value.hashCode();
  }

  @Override
  public final String toString() {
    return value;
  }
}
