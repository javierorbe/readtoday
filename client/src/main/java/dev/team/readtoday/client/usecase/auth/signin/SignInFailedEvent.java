package dev.team.readtoday.client.usecase.auth.signin;

public class SignInFailedEvent {

  private final String reason;

  public SignInFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
