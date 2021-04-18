package dev.team.readtoday.server.jwt.domain;

import java.io.Serial;

public final class InvalidJwtToken extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -8260762809308518345L;

  public InvalidJwtToken(Exception cause) {
    super(cause);
  }
}
