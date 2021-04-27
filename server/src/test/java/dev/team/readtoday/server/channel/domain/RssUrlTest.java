package dev.team.readtoday.server.channel.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class RssUrlTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "https://metricool.com/feed/",
      "https://www.josefacchin.com/feed/",
      "https://e00-marca.uecdn.es/rss/futbol/athletic.xml"
  })
  void shouldNotThrowExceptionIfItIsValid(String url) {
    assertDoesNotThrow(() -> RssUrl.create(url));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "htt:-//metric_.com",
      "https://www.google.es/"
  })
  void shouldThrowExceptionIfItIsNotValid(String rssUrl) {
    assertThrows(InvalidRssUrl.class, () -> RssUrl.create(rssUrl));
  }
}
