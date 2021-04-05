package dev.team.readtoday.server.user.infrastructure.controller.signin;

public final class SignInRequest {

  private String accessToken;

  String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
