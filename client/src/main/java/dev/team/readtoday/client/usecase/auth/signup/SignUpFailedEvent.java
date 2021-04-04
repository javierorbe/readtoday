package dev.team.readtoday.client.usecase.auth.signup;

public final class SignUpFailedEvent {

  private final String reason;

  public SignUpFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
