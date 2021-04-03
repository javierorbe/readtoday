package dev.team.readtoday.client.auth;

public final class SignUpRequest {

  private final String accessToken;
  private final String username;

  SignUpRequest(String accessToken, String username) {
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
