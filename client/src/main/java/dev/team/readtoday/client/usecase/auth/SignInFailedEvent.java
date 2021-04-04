package dev.team.readtoday.client.usecase.auth;

public class SignInFailedEvent {

  private final String reason;

  SignInFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
