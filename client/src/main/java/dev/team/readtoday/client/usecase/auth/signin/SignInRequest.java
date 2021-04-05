package dev.team.readtoday.client.usecase.auth.signin;

public final class SignInRequest {

  private final String accessToken;

  public SignInRequest(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
