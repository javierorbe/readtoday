package dev.team.readtoday.client.auth;

public final class SignUpFailedEvent {

  private final String reason;

  SignUpFailedEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
