package dev.team.readtoday.server.user.infrastructure.controller.signin;

public final class SignInRequest {

  private String token;

  String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
