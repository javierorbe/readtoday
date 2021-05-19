package dev.team.readtoday.server.settings.application.findbytimezone;

import dev.team.readtoday.server.settings.application.SettingsCollectionQueryResponse;
import dev.team.readtoday.server.settings.application.SettingsQueryResponse;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.util.stream.Collectors;

@Service
public final class FindSettingsByTimeZoneQueryHandler
    implements QueryHandler<FindSettingsByTimeZoneQuery, SettingsCollectionQueryResponse> {

  private final SearchSettingsByTimeZone search;

  public FindSettingsByTimeZoneQueryHandler(SearchSettingsByTimeZone search) {
    this.search = search;
  }

  @Override
  public SettingsCollectionQueryResponse handle(FindSettingsByTimeZoneQuery query) {
    var searchResult = search.search(TimeZone.fromString(query.getZoneId()));
    var collection =
        searchResult.stream()
            .map(this::toResponse)
            .collect(Collectors.toUnmodifiableSet());
    return new SettingsCollectionQueryResponse(collection);
  }

  private SettingsQueryResponse toResponse(Settings settings) {
    return new SettingsQueryResponse(
        settings.getUserId().toString(),
        settings.getTimeZone().toString(),
        settings.getNotificationPreference().toString()
    );
  }
}
