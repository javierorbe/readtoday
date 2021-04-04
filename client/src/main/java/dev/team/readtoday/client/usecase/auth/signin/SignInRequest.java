package dev.team.readtoday.client.usecase.auth.signin;

public final class SignInRequest {

  private final String token;

  public SignInRequest(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
