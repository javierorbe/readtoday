package dev.team.readtoday.server.settings.domain;

import java.util.Collection;

public interface SettingsRepository {

  void save(Settings settings);

  /** Returns the {@link Settings} that have a given time zone. */
  Collection<Settings> getWithTimeZone(TimeZone timeZone);
}
