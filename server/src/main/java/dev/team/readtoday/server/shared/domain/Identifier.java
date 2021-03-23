package dev.team.readtoday.server.shared.domain;

import java.util.UUID;

public class Identifier {

  private final UUID value;

  protected Identifier(UUID value) {
    this.value = value;
  }

  public final UUID getValue() {
    return value;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    Identifier id = (Identifier) o;
    return value.equals(id.value);
  }

  @Override
  public final int hashCode() {
    return value.hashCode();
  }

  @Override
  public final String toString() {
    return value.toString();
  }
}
