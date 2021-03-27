package dev.team.readtoday.server.shared.infrastructure;

import java.io.Serial;

final class InvalidJwtToken extends Exception {

  @Serial
  private static final long serialVersionUID = -8260762809308518345L;

  InvalidJwtToken(Exception cause) {
    super(cause);
  }
}
