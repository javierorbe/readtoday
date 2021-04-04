package dev.team.readtoday.client.usecase.auth;

public final class SignUpRequestReadyEvent {

  private final String accessToken;
  private final String username;

  public SignUpRequestReadyEvent(String accessToken, String username) {
    this.accessToken = accessToken;
    this.username = username;
  }

  String getAccessToken() {
    return accessToken;
  }

  String getUsername() {
    return username;
  }
}
