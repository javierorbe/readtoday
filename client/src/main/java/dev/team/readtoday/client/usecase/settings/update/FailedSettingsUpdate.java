package dev.team.readtoday.client.usecase.settings.update;

public final class FailedSettingsUpdate {

  private final String reason;

  FailedSettingsUpdate(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
