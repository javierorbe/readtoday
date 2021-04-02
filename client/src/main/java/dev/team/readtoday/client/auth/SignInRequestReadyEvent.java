package dev.team.readtoday.client.auth;

public final class SignInRequestReadyEvent {

  private final String accessToken;

  public SignInRequestReadyEvent(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
