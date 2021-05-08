package dev.team.readtoday.server.shared.domain;

import java.util.UUID;

public class CustomListId extends Identifier {

  protected CustomListId(UUID value) {
    super(value);
  }

  public static CustomListId random() {
    return new CustomListId(UUID.randomUUID());
  }

  public static CustomListId fromString(String uuid) {
    return new CustomListId(UUID.fromString(uuid));
  }
}
