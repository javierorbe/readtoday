package dev.team.readtoday.server.settings.infrastructure.persistance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SETTINGS;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

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
    dsl.insertInto(SETTINGS, SETTINGS.USER_ID, SETTINGS.PREFERENCE, SETTINGS.TIMEZONE)
        .values(settings.getUserId().toString(),
            PREFERENCES.get(settings.getNotificationPreference()),
            settings.getTimeZone().toString())
        .onDuplicateKeyUpdate()
        .set(SETTINGS.PREFERENCE, PREFERENCES.get(settings.getNotificationPreference())).execute();
  }

  @Override
  public Collection<Settings> getWithTimeZone(TimeZone timeZone) {
    Result<Record2<String, String>> results =
        dsl.select(SETTINGS.USER_ID, SETTINGS.PREFERENCE).from(SETTINGS)
            .where(SETTINGS.TIMEZONE.eq(timeZone.toString())).fetch();

    Collection<Settings> settings = new ArrayList<Settings>();

    for (Record2<String, String> result : results) {
      UserId userId = UserId.fromString(result.get(SETTINGS.USER_ID));
      NotificationPreference preference =
          PREFERENCES.inverse().get(result.get(SETTINGS.PREFERENCE));
      settings.add(new Settings(userId, preference, timeZone));
    }
    return settings;
  }
}
