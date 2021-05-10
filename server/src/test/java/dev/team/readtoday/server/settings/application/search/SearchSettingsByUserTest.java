package dev.team.readtoday.server.settings.application.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.UserId;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchSettingsByUserTest {

  @Test
  void shouldReturnSettingsForUser() {
    SettingsRepository repository = mock(SettingsRepository.class);
    UserId userId = UserId.random();
    Optional<Settings> expectedSettings = Optional.of(new Settings(userId,
        NotificationPreference.NONE, TimeZone.fromString(ZoneId.systemDefault().toString())));

    when(repository.getWithUserId(userId)).thenReturn(expectedSettings);
    SearchSettingsByUser searchSettings = new SearchSettingsByUser(repository);
    Optional<Settings> actualSettings = searchSettings.search(userId);

    assertEquals(expectedSettings, actualSettings);
  }

  @Test
  void shouldNotReturnSettingsForUser() {
    SettingsRepository repository = mock(SettingsRepository.class);
    UserId userId = UserId.random();
    Optional<Settings> expectedSettings = Optional.of(new Settings(userId,
        NotificationPreference.NONE, TimeZone.fromString(ZoneId.systemDefault().toString())));

    when(repository.getWithUserId(userId)).thenReturn(expectedSettings);
    SearchSettingsByUser searchSettings = new SearchSettingsByUser(repository);
    Optional<Settings> actualSettings = searchSettings.search(UserId.random());

    assertNotEquals(expectedSettings, actualSettings);
  }
}
