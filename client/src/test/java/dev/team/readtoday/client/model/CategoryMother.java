package dev.team.readtoday.client.model;

import java.util.UUID;

enum CategoryMother {
  ;

  static Category withName(String name) {
    return new Category(UUID.randomUUID(), name);
  }
}
