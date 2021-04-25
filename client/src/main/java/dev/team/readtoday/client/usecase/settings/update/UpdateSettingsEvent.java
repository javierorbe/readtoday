package dev.team.readtoday.client.usecase.settings.update;

import dev.team.readtoday.client.model.NotificationPreference;
import java.time.ZoneOffset;

public final class UpdateSettingsEvent {

  private final ZoneOffset zoneOffset;
  private final NotificationPreference notificationPref;

  public UpdateSettingsEvent(ZoneOffset zoneOffset,
                             NotificationPreference notificationPref) {
    this.notificationPref = notificationPref;
    this.zoneOffset = zoneOffset;
  }

  ZoneOffset getZoneOffset() {
    return zoneOffset;
  }

  NotificationPreference getNotificationPref() {
    return notificationPref;
  }
}
