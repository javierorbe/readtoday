package dev.team.readtoday.server.user.application.signup;

import dev.team.readtoday.server.shared.domain.bus.command.Command;

public final class SignUpUserCommand implements Command {

  private final String userId;
  private final String username;
  private final String accessToken;

  public SignUpUserCommand(String userId, String username, String accessToken) {
    this.userId = userId;
    this.username = username;
    this.accessToken = accessToken;
  }

  public String getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
