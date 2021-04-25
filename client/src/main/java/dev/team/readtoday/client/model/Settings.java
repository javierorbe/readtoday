package dev.team.readtoday.client.model;

import java.time.ZoneId;

public final class Settings {

  private ZoneId zoneId;
  private NotificationPreference notificationPreference;

  public Settings(ZoneId zoneId,
      NotificationPreference notificationPreference) {
    this.zoneId = zoneId;
    this.notificationPreference = notificationPreference;
  }

  public ZoneId getZoneId() {
    return zoneId;
  }

  public void setZoneId(ZoneId zoneId) {
    this.zoneId = zoneId;
  }

  public NotificationPreference getNotificationPreference() {
    return notificationPreference;
  }

  public void setNotificationPreference(
      NotificationPreference notificationPreference) {
    this.notificationPreference = notificationPreference;
  }
}
