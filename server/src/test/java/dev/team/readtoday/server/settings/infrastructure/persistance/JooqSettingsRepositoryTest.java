package dev.team.readtoday.server.settings.infrastructure.persistance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SETTINGS;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.Settings;
import dev.team.readtoday.server.settings.domain.SettingsRepository;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqSettingsRepositoryTest extends BaseJooqIntegrationTest {

  private static SettingsRepository repositorySettings;
  private static UserRepository repositoryUser;
  private static User user;

  @BeforeAll
  static void beforeAll() {
    start(SETTINGS, USER);
    repositorySettings = getRepository(JooqSettingsRepository.class);
    repositoryUser = getRepository(JooqUserRepository.class);
    user = UserMother.random();
    repositoryUser.save(user);

  }

  @AfterAll
  static void afterAll() {
    clearAndShutdown();
  }

  @Test
  void shouldSaveSettings() {
    Settings settings = new Settings(user.getId(), NotificationPreference.NONE,
        TimeZone.fromString(ZoneId.systemDefault().toString()));

    assertDoesNotThrow(() -> repositorySettings.save(settings));
  }

  @Test
  void shouldUpdateExistingSettings() {
    Settings origSettings = new Settings(user.getId(), NotificationPreference.NONE,
        TimeZone.fromString(ZoneId.systemDefault().toString()));
    repositorySettings.save(origSettings);

    Settings newSettings =
        new Settings(user.getId(), NotificationPreference.WEEKLY, origSettings.getTimeZone());
    assertDoesNotThrow(() -> repositorySettings.save(newSettings));
  }

  @Test
  void shouldReturnSettingCollection() {
    Settings settings = new Settings(user.getId(), NotificationPreference.NONE,
        TimeZone.fromString(ZoneId.systemDefault().toString()));

    repositorySettings.save(settings);

    Collection<Settings> collectSettings =
        repositorySettings.getWithTimeZone(settings.getTimeZone());
    List<Settings> listSettings = new ArrayList<Settings>(collectSettings);

    assertEquals(1, listSettings.size());
    assertEquals(settings.getUserId(), listSettings.get(0).getUserId());
    assertEquals(settings.getNotificationPreference(),
        listSettings.get(0).getNotificationPreference());
    assertEquals(settings.getTimeZone(), listSettings.get(0).getTimeZone());
  }

  @Test
  void shouldReturnSettingsFromUser() {
    Settings settings = new Settings(user.getId(), NotificationPreference.NONE,
        TimeZone.fromString(ZoneId.systemDefault().toString()));

    repositorySettings.save(settings);

    Optional<Settings> opSettings = repositorySettings.getWithUserId(user.getId());

    assertEquals(settings.getUserId(), opSettings.get().getUserId());
    assertEquals(settings.getNotificationPreference(),
        opSettings.get().getNotificationPreference());
    assertEquals(settings.getTimeZone(), opSettings.get().getTimeZone());
  }
}
