package dev.team.readtoday.server.user.application.profile;

import java.io.Serial;

public final class ProfileFetchingFailed extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 9122222716247786898L;

  public ProfileFetchingFailed(String message, Exception cause) {
    super(message, cause);
  }
}
