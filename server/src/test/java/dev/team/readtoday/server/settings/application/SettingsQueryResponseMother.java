package dev.team.readtoday.server.settings.application;

import java.time.ZoneId;

public enum SettingsQueryResponseMother {
  ;

  public static SettingsQueryResponse withUserId(String userId) {
    return new SettingsQueryResponse(
        userId,
        ZoneId.systemDefault().getId(),
        "daily"
    );
  }
}
