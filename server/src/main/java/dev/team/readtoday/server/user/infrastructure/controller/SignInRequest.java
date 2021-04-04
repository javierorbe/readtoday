package dev.team.readtoday.server.user.infrastructure.controller;

public final class SignInRequest {

  private String token;

  String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
