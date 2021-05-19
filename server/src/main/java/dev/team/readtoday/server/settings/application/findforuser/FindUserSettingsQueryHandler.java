package dev.team.readtoday.server.settings.application.findforuser;

import dev.team.readtoday.server.settings.application.SettingsQueryResponse;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;

@Service
public final class FindUserSettingsQueryHandler
    implements QueryHandler<FindUserSettingsQuery, SettingsQueryResponse> {

  private final FindUserSettings findUserSettings;

  public FindUserSettingsQueryHandler(FindUserSettings findUserSettings) {
    this.findUserSettings = findUserSettings;
  }

  @Override
  public SettingsQueryResponse handle(FindUserSettingsQuery query) {
    var userId = UserId.fromString(query.getUserId());
    var settings = findUserSettings.apply(userId);
    return new SettingsQueryResponse(
        settings.getUserId().toString(),
        settings.getTimeZone().toString(),
        settings.getNotificationPreference().toString()
    );
  }
}
