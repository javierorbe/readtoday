package dev.team.readtoday.server.settings.application;

import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;

public final class SettingsQueryResponse implements QueryResponse {

  private final String userId;
  private final String zoneId;
  private final String notificationPref;

  public SettingsQueryResponse(String userId, String zoneId, String notificationPref) {
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
