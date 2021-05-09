package dev.team.readtoday.server.settings.infrastructure.persistance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SETTINGS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;

@Service
public class JooqSettingsRepository implements SettingsRepository {

  private static final BiMap<NotificationPreference, String> PREFERENCES =
      HashBiMap.create(Map.of(NotificationPreference.NONE, "none", NotificationPreference.DAILY,
          "daily", NotificationPreference.WEEKLY, "weekly"));

  private final DSLContext dsl;

  public JooqSettingsRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Settings settings) {
    dsl.insertInto(SETTINGS, SETTINGS.USER_ID, SETTINGS.PREF_TYPE, SETTINGS.TIMEZONE)
        .values(settings.getUserId().toString(),
            PREFERENCES.get(settings.getNotificationPreference()),
            settings.getTimeZone().toString())
        .onDuplicateKeyUpdate()
        .set(SETTINGS.PREF_TYPE, PREFERENCES.get(settings.getNotificationPreference())).execute();
  }

  @Override
  public Collection<Settings> getWithTimeZone(TimeZone timeZone) {
    Result<Record2<String, String>> results = dsl.select(SETTINGS.USER_ID, SETTINGS.PREF_TYPE)
        .from(SETTINGS).where(SETTINGS.TIMEZONE.eq(timeZone.toString())).fetch();

    Collection<Settings> settings = new ArrayList<Settings>();

    for (Record2<String, String> result : results) {
      UserId userId = UserId.fromString(result.get(SETTINGS.USER_ID));
      NotificationPreference preference = PREFERENCES.inverse().get(result.get(SETTINGS.PREF_TYPE));
      settings.add(new Settings(userId, preference, timeZone));
    }
    return settings;
  }

  @Override
  public Optional<Settings> getWithUserId(UserId userId) {
    Record2<String, String> results = dsl.select(SETTINGS.PREF_TYPE, SETTINGS.TIMEZONE)
        .from(SETTINGS).where(SETTINGS.USER_ID.eq(userId.toString())).fetchOne();

    if (results == null) {
      return Optional.empty();
    }

    NotificationPreference preference = PREFERENCES.inverse().get(results.value1());
    TimeZone timeZone = TimeZone.fromString(results.value2());
    return Optional.of(new Settings(userId, preference, timeZone));

  }
}
