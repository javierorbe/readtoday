package dev.team.readtoday.client.usecase.settings.get;

public final class GetSettingsResponse {

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
