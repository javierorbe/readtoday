package dev.team.readtoday.client.usecase.auth.signup;

public final class SuccessfulSignUpEvent {

  private final String jwtToken;

  public SuccessfulSignUpEvent(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }
}
