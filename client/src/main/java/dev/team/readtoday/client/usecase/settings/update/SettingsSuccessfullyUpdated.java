package dev.team.readtoday.client.usecase.settings.update;

import dev.team.readtoday.client.model.NotificationPreference;
import java.time.ZoneOffset;

public final class SettingsSuccessfullyUpdated {

  private final ZoneOffset zoneOffset;
  private final NotificationPreference notificationPref;

  SettingsSuccessfullyUpdated(ZoneOffset zoneOffset,
                                     NotificationPreference notificationPref) {
    this.zoneOffset = zoneOffset;
    this.notificationPref = notificationPref;
  }

  public ZoneOffset getZoneOffset() {
    return zoneOffset;
  }

  public NotificationPreference getNotificationPref() {
    return notificationPref;
  }
}
