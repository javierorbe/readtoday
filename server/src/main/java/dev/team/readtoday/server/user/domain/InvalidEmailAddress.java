package dev.team.readtoday.server.user.domain;

import java.io.Serial;

public final class InvalidEmailAddress extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1427197240199832177L;

  public InvalidEmailAddress(String message) {
    super(message);
  }
}
