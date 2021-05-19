package dev.team.readtoday.server.settings.application.findbytimezone;

import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;

@Service
public final class SearchSettingsByTimeZone {

  private final SettingsRepository repository;

  public SearchSettingsByTimeZone(SettingsRepository repository) {
    this.repository = repository;
  }

  public Collection<Settings> search(TimeZone timeZone) {
    return repository.getWithTimeZone(timeZone);
  }
}
