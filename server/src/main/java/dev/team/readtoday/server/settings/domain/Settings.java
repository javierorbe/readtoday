package dev.team.readtoday.server.settings.domain;

import dev.team.readtoday.server.shared.domain.UserId;

public final class Settings {

  private final UserId userId;
  private final NotificationPreference notificationPref;
  private final TimeZone timeZone;

  public Settings(UserId userId,
                  NotificationPreference notificationPref,
                  TimeZone timeZone) {
    this.userId = userId;
    this.notificationPref = notificationPref;
    this.timeZone = timeZone;
  }

  public UserId getUserId() {
    return userId;
  }

  public NotificationPreference getNotificationPreference() {
    return notificationPref;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }
}
