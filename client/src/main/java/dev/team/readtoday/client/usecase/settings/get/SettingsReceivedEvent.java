package dev.team.readtoday.client.usecase.settings.get;

import dev.team.readtoday.client.model.NotificationPreference;
import java.time.ZoneId;

public final class SettingsReceivedEvent {

  private final ZoneId zoneId;
  private final NotificationPreference notificationPreference;

  public SettingsReceivedEvent(String zoneId,
      String notificationPreference) {
    this.zoneId = ZoneId.of(zoneId);
    this.notificationPreference = NotificationPreference.fromString(notificationPreference);
  }

  public ZoneId getZoneId() {
    return zoneId;
  }

  public NotificationPreference getNotificationPreference() {
    return notificationPreference;
  }
}
