package dev.team.readtoday.server.settings.infrastructure.controller.put;

public final class SettingsRequest {

  private String zoneId;
  private String notificationPref;

  String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  String getNotificationPref() {
    return notificationPref;
  }

  public void setNotificationPref(String notificationPref) {
    this.notificationPref = notificationPref;
  }
}
