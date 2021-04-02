package dev.team.readtoday.client.auth;

public final class SignUpRequest {

  private final String token;
  private final String username;

  SignUpRequest(String token, String username) {
    this.token = token;
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public String getUsername() {
    return username;
  }
}
