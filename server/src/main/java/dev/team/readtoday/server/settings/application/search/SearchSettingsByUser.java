package dev.team.readtoday.server.settings.application.search;

import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Optional;

public class SearchSettingsByUser {

  private final SettingsRepository repository;

  public SearchSettingsByUser(SettingsRepository repository) {
    this.repository = repository;
  }

  public Optional<Settings> search(UserId userId) {
    return repository.getWithUserId(userId);
  }
}
