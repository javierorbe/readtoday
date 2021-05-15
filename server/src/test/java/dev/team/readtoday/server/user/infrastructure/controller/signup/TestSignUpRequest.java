package dev.team.readtoday.server.user.infrastructure.controller.signup;

public final class TestSignUpRequest {

  private final String accessToken;
  private final String username;

  public TestSignUpRequest(String accessToken, String username) {
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
