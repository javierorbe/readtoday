package dev.team.readtoday.client.usecase.settings.get;

import dev.team.readtoday.client.model.NotificationPreference;
import java.time.ZoneOffset;

public final class SettingsReceivedEvent {

  private final ZoneOffset zoneOffset;
  private final NotificationPreference notificationPref;

  SettingsReceivedEvent(String zoneId, String notificationPref) {
    this.zoneOffset = ZoneOffset.of(zoneId);
    this.notificationPref = NotificationPreference.fromString(notificationPref);
  }

  public ZoneOffset getZoneOffset() {
    return zoneOffset;
  }

  public NotificationPreference getNotificationPreference() {
    return notificationPref;
  }
}
