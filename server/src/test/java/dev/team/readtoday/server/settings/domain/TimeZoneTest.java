package dev.team.readtoday.server.settings.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class TimeZoneTest {

  @Test
  void shouldNotThrowExceptionIfValidZoneId() {
    assertDoesNotThrow(() -> TimeZone.fromString(ZoneId.systemDefault().getId()));
  }
}
