package dev.team.readtoday.client.usecase.auth.signin;

public class SuccessfulSignInEvent {

  private final String jwtToken;

  public SuccessfulSignInEvent(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }
}
