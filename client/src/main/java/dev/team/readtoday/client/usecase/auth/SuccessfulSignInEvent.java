package dev.team.readtoday.client.usecase.auth;

public class SuccessfulSignInEvent {

  private final String jwtToken;

  SuccessfulSignInEvent(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }
}
