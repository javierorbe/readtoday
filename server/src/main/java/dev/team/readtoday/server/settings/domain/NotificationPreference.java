package dev.team.readtoday.server.settings.domain;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public enum NotificationPreference {
  NONE("none"),
  DAILY("daily"),
  WEEKLY("weekly"),
  ;

  private static final Map<String, NotificationPreference> REVERSE_MAP
      = Arrays.stream(values()).collect(toUnmodifiableMap(np -> np.value, np -> np));

  private final String value;

  NotificationPreference(String value) {
    this.value = value;
  }

  public static NotificationPreference fromString(String value) {
    return Objects.requireNonNull(REVERSE_MAP.get(value));
  }

  @Override
  public String toString() {
    return value;
  }
}
