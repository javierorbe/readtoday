package dev.team.readtoday.server.category.domain;

import dev.team.readtoday.server.shared.domain.Identifier;
import java.util.UUID;

public class CategoryId extends Identifier {

  private CategoryId(UUID value) {
    super(value);
  }

  public static CategoryId random() {
    return new CategoryId(UUID.randomUUID());
  }

  public static CategoryId fromString(String uuid) {
    return new CategoryId((UUID.fromString(uuid)));
  }
}
