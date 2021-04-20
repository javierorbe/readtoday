package dev.team.readtoday.server.settings.domain;

import java.time.ZoneId;

public final class TimeZone {

  private final ZoneId zoneId;

  private TimeZone(ZoneId zoneId) {
    this.zoneId = zoneId;
  }

  public static TimeZone fromString(String zoneId) {
    return new TimeZone(ZoneId.of(zoneId));
  }

  @Override
  public String toString() {
    return zoneId.getId();
  }
}
