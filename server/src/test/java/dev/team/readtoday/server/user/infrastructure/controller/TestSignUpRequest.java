package dev.team.readtoday.server.user.infrastructure.controller;

public final class TestSignUpRequest {

  private final String accessToken;
  private final String username;

  TestSignUpRequest(String accessToken, String username) {
    this.accessToken = accessToken;
    this.username = username;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getUsername() {
    return username;
  }
}
