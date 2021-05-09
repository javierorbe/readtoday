package dev.team.readtoday.server.settings.domain;

import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Collection;
import java.util.Optional;

public interface SettingsRepository {

  void save(Settings settings);

  /** Returns the {@link Settings} that have a given time zone. */
  Collection<Settings> getWithTimeZone(TimeZone timeZone);

  /** Returns the {@link Settings} that have a given user id. */
  Optional<Settings> getWithUserId(UserId userId);
}
