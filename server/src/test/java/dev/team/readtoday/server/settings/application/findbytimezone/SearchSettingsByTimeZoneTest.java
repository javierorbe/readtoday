package dev.team.readtoday.server.settings.application.findbytimezone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.UserId;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class SearchSettingsByTimeZoneTest {

  @Test
  void shouldReturnSettingsForTimeZone() {
    SettingsRepository repository = mock(SettingsRepository.class);
    TimeZone timeZone = TimeZone.fromString(ZoneId.systemDefault().toString());
    Collection<Settings> expectedSettings = new ArrayList<>();
    expectedSettings.add(new Settings(UserId.random(), NotificationPreference.NONE, timeZone));

    when(repository.getWithTimeZone(timeZone)).thenReturn(expectedSettings);
    SearchSettingsByTimeZone searchSettings = new SearchSettingsByTimeZone(repository);
    Collection<Settings> actualSettings = searchSettings.search(timeZone);

    assertEquals(expectedSettings, actualSettings);
  }

  @Test
  void shouldNotReturnSettingsForTimeZone() {
    SettingsRepository repository = mock(SettingsRepository.class);
    TimeZone timeZone = TimeZone.fromString("Europe/Paris");
    Collection<Settings> expectedSettings = new ArrayList<>();
    expectedSettings.add(new Settings(UserId.random(), NotificationPreference.NONE, timeZone));

    when(repository.getWithTimeZone(timeZone)).thenReturn(expectedSettings);
    SearchSettingsByTimeZone searchSettings = new SearchSettingsByTimeZone(repository);
    Collection<Settings> actualSettings =
        searchSettings.search(TimeZone.fromString("Europe/Madrid"));

    assertNotEquals(expectedSettings, actualSettings);
  }
}
