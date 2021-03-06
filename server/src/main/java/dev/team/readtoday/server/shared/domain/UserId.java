package dev.team.readtoday.server.shared.domain;

import java.util.UUID;

public final class UserId extends Identifier {

  private UserId(UUID value) {
    super(value);
  }

  public static UserId random() {
    return new UserId(UUID.randomUUID());
  }

  public static UserId fromString(String uuid) {
    return new UserId(UUID.fromString(uuid));
  }
}
