package dev.team.readtoday.server.user.infrastructure.controller;

public final class SignUpRequest {

  private String token;
  private String username;

  String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
