package dev.team.readtoday.server.user.infrastructure.controller.signin;

public final class TestSignInRequest {

  private final String accessToken;

  TestSignInRequest(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
