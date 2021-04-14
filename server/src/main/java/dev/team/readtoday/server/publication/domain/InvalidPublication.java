package dev.team.readtoday.server.publication.domain;

import java.io.Serial;

public final class InvalidPublication extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 3885280969105690455L;

  InvalidPublication(String message) {
    super(message);
  }
}
