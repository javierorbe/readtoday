package dev.team.readtoday.server.settings.application.findbytimezone;

import dev.team.readtoday.server.settings.application.SettingsCollectionQueryResponse;
import dev.team.readtoday.server.shared.domain.bus.query.Query;

public final class FindSettingsByTimeZoneQuery implements Query<SettingsCollectionQueryResponse> {

  private final String zoneId;

  public FindSettingsByTimeZoneQuery(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getZoneId() {
    return zoneId;
  }
}
