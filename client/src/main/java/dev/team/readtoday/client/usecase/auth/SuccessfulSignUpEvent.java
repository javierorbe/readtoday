package dev.team.readtoday.client.usecase.auth;

public final class SuccessfulSignUpEvent {

  private final String jwtToken;

  SuccessfulSignUpEvent(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }
}
