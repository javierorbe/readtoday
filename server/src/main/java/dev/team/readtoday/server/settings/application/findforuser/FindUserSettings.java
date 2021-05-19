package dev.team.readtoday.server.settings.application.findforuser;

import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.function.Function;

@Service
public final class FindUserSettings implements Function<UserId, Settings> {

  private final SettingsRepository repository;

  public FindUserSettings(SettingsRepository repository) {
    this.repository = repository;
  }

  @Override
  public Settings apply(UserId userId) {
    return repository.getWithUserId(userId).get();
  }
}
