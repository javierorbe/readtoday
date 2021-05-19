package dev.team.readtoday.server.settings.application.findforuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.UserId;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class FindUserSettingsTest {

  @Test
  void shouldReturnSettingsForUser() {
    SettingsRepository repository = mock(SettingsRepository.class);
    UserId userId = UserId.random();
    Settings expectedSettings = new Settings(
        userId,
        NotificationPreference.NONE,
        TimeZone.fromString(ZoneId.systemDefault().toString())
    );

    when(repository.getWithUserId(userId)).thenReturn(Optional.of(expectedSettings));
    FindUserSettings searchSettings = new FindUserSettings(repository);
    Settings actualSettings = searchSettings.apply(userId);

    assertEquals(expectedSettings.getUserId(), actualSettings.getUserId());
    assertEquals(expectedSettings.getNotificationPreference(), actualSettings.getNotificationPreference());
    assertEquals(expectedSettings.getTimeZone(), actualSettings.getTimeZone());
  }
}
