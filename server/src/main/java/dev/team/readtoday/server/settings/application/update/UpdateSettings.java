package dev.team.readtoday.server.settings.application.update;

import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;

@Service
public final class UpdateSettings {

  private final SettingsRepository repository;

  public UpdateSettings(SettingsRepository repository) {
    this.repository = repository;
  }

  public void update(UserId userId, NotificationPreference notificationPref, TimeZone timeZone) {
    repository.save(new Settings(userId, notificationPref, timeZone));
  }
}
