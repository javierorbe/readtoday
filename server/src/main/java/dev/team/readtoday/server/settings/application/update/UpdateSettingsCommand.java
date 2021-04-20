package dev.team.readtoday.server.settings.application.update;

import dev.team.readtoday.server.shared.domain.bus.command.Command;

public final class UpdateSettingsCommand implements Command {

  private final String userId;
  private final String zoneId;
  private final String notificationPref;

  public UpdateSettingsCommand(String userId, String zoneId, String notificationPref) {
    this.userId = userId;
    this.zoneId = zoneId;
    this.notificationPref = notificationPref;
  }

  public String getUserId() {
    return userId;
  }

  public String getZoneId() {
    return zoneId;
  }

  public String getNotificationPreference() {
    return notificationPref;
  }
}
