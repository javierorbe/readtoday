package dev.team.readtoday.client.usecase.settings.update;

import dev.team.readtoday.client.model.NotificationPreference;
import java.time.ZoneId;

public final class SettingsSuccessfullyUpdated {

  private final ZoneId zoneId;
  private final NotificationPreference notificationPreference;

  public SettingsSuccessfullyUpdated(ZoneId zoneId,
      NotificationPreference notificationPreference) {
    this.zoneId = zoneId;
    this.notificationPreference = notificationPreference;
  }

  public ZoneId getZoneId() {
    return zoneId;
  }

  public NotificationPreference getNotificationPreference() {
    return notificationPreference;
  }
}
