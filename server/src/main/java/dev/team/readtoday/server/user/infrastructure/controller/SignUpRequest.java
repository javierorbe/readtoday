package dev.team.readtoday.server.user.infrastructure.controller;

public final class SignUpRequest {

  private String accessToken;
  private String username;

  String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
