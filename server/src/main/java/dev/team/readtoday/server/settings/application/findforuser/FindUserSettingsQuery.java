package dev.team.readtoday.server.settings.application.findforuser;

import dev.team.readtoday.server.settings.application.SettingsQueryResponse;
import dev.team.readtoday.server.shared.domain.bus.query.Query;

public final class FindUserSettingsQuery implements Query<SettingsQueryResponse> {

  private final String userId;

  public FindUserSettingsQuery(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }
}
