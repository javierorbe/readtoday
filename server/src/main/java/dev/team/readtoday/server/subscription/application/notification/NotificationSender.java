package dev.team.readtoday.server.subscription.application.notification;

import static java.util.stream.Collectors.groupingBy;

import dev.team.readtoday.server.publication.application.formatted.FormattedTopPublicationsQuery;
import dev.team.readtoday.server.settings.application.SettingsQueryResponse;
import dev.team.readtoday.server.settings.application.findbytimezone.FindSettingsByTimeZoneQuery;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.subscription.application.GetUserSubscriptions;
import dev.team.readtoday.server.user.application.sendemail.SendEmailCommand;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public final class NotificationSender implements Runnable {

  private static final long SECONDS_IN_AN_HOUR = TimeUnit.HOURS.toSeconds(1L);

  private static final int NOTIFICATION_SEND_HOUR = 9;
  private static final String NOTIFICATION_EMAIL_SUBJECT = "ReadToday Feed";

  private final CommandBus commandBus;
  private final QueryBus queryBus;

  private final GetUserSubscriptions getUserSubscriptions;

  private final Clock clock;

  private final ScheduledExecutorService executorService =
      Executors.newSingleThreadScheduledExecutor();

  public NotificationSender(CommandBus commandBus,
                            QueryBus queryBus,
                            Clock clock,
                            GetUserSubscriptions getUserSubscriptions) {
    this.commandBus = commandBus;
    this.queryBus = queryBus;
    this.clock = clock;
    this.getUserSubscriptions = getUserSubscriptions;
  }

  @Override
  public void run() {
    startExecutionLoop();
  }

  private void startExecutionLoop() {
    long untilNextHour = calculateSecondsUntilNextHour();
    executorService.scheduleAtFixedRate(this::sendNotifications,
        untilNextHour,
        SECONDS_IN_AN_HOUR,
        TimeUnit.SECONDS);
  }

  private long calculateSecondsUntilNextHour() {
    LocalDateTime start = LocalDateTime.now(clock);
    LocalDateTime end = start.plusHours(1L).truncatedTo(ChronoUnit.HOURS);

    Duration duration = Duration.between(start, end);
    return duration.toSeconds();
  }

  private void sendNotifications() {
    ZoneId zoneId = getNotificationZoneId();
    var settingsMap = getSettingsByNotificationPreference(zoneId.getId());

    LocalDate today = LocalDate.now(zoneId);

    if (isMonday(today)) {
      sendNotificationsTo(getWeekly(settingsMap));
    }
    sendNotificationsTo(getDaily(settingsMap));
  }

  private ZoneId getNotificationZoneId() {
    // TODO
    throw new UnsupportedOperationException("Not implemented, yet.");
  }

  private void sendNotificationsTo(Iterable<SettingsQueryResponse> settingsList) {
    settingsList.forEach(this::sendNotificationTo);
  }

  private void sendNotificationTo(SettingsQueryResponse settings) {
    String userId = settings.getUserId();
    String content = buildEmailContentFor(userId);
    commandBus.dispatch(new SendEmailCommand(userId, NOTIFICATION_EMAIL_SUBJECT, content));
  }

  private static List<SettingsQueryResponse>
  getWeekly(Map<String, ? extends List<SettingsQueryResponse>> settingsMap) {
    return settingsMap.get("weekly");
  }

  private static List<SettingsQueryResponse>
  getDaily(Map<String, ? extends List<SettingsQueryResponse>> settingsMap) {
    return settingsMap.get("daily");
  }

  private static boolean isMonday(LocalDate date) {
    return date.getDayOfWeek() == DayOfWeek.MONDAY;
  }

  private String buildEmailContentFor(String userId) {
    List<String> channelIds = getSubscribedChannelIds(userId);
    List<String> publications = getFormattedPublicationsOfChannels(channelIds);
    return String.join("", publications);
  }

  private List<String> getSubscribedChannelIds(String userId) {
    var subscriptions = getUserSubscriptions.search(UserId.fromString(userId));
    return subscriptions.stream().map(sub -> sub.getChannelId().toString()).toList();
  }

  private List<String> getFormattedPublicationsOfChannels(List<String> channelIds) {
    var response = queryBus.ask(new FormattedTopPublicationsQuery(channelIds));
    return response.getFormattedPublications();
  }

  private Map<String, List<SettingsQueryResponse>>
  getSettingsByNotificationPreference(String zoneId) {
    var response = queryBus.ask(new FindSettingsByTimeZoneQuery(zoneId));
    var settingsCollection = response.getCollection();
    return settingsCollection.stream()
        .collect(groupingBy(SettingsQueryResponse::getNotificationPreference));
  }
}
