package dev.team.readtoday.client.auth;

public final class SignInRequest {

  private final String token;

  SignInRequest(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
