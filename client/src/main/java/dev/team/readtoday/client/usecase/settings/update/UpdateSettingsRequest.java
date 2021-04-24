package dev.team.readtoday.client.usecase.settings.update;

public final class UpdateSettingsRequest {

  private final String zoneId;
  private final String notificationPref;

  UpdateSettingsRequest(String zoneId, String notificationPref) {
    this.zoneId = zoneId;
    this.notificationPref = notificationPref;
  }

  public String getZoneId() {
    return zoneId;
  }

  public String getNotificationPref() {
    return notificationPref;
  }
}
