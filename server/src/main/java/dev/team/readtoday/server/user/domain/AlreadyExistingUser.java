package dev.team.readtoday.server.user.domain;

import java.io.Serial;

public final class AlreadyExistingUser extends Exception {

  @Serial
  private static final long serialVersionUID = 9052049375680954223L;

  public AlreadyExistingUser(String message) {
    super(message);
  }
}
