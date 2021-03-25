package dev.team.readtoday.server.user.application;

import java.io.Serial;

public final class AuthProcessFailed extends Exception {

  @Serial
  private static final long serialVersionUID = 9122222716247786898L;

  public AuthProcessFailed(String message, Exception cause) {
    super(message, cause);
  }
}
