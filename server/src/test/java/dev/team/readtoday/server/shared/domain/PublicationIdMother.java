package dev.team.readtoday.server.shared.domain;

import java.util.UUID;

public enum PublicationIdMother {
  ;

  public static PublicationId random() {
    return new PublicationId(UUID.randomUUID().toString());
  }
}
