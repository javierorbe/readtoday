package dev.team.readtoday.server.settings.infrastructure.controller.get;

import dev.team.readtoday.server.settings.application.SettingsQueryResponse;

public final class SettingsResponse {

  private final String zoneId;
  private final String notificationPref;

  SettingsResponse(String zoneId, String notificationPref) {
    this.zoneId = zoneId;
    this.notificationPref = notificationPref;
  }

  public String getZoneId() {
    return zoneId;
  }

  public String getNotificationPref() {
    return notificationPref;
  }

  static SettingsResponse fromQueryResponse(SettingsQueryResponse response) {
    return new SettingsResponse(response.getZoneId(), response.getNotificationPreference());
  }
}
