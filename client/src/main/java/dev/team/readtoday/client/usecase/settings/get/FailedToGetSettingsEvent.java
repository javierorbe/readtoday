package dev.team.readtoday.client.usecase.settings.get;

public final class FailedToGetSettingsEvent {

  private final String reason;

  FailedToGetSettingsEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
