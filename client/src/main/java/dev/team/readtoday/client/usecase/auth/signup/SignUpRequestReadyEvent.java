package dev.team.readtoday.client.usecase.auth.signup;

public final class SignUpRequestReadyEvent {

  private final String accessToken;
  private final String username;

  public SignUpRequestReadyEvent(String accessToken, String username) {
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
