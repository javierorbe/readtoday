package dev.team.readtoday.server.channel.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class UrlTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "https://metricool.com/feed/",
      "https://www.youtube.com/feeds/videos.xml?channel_id=UCZiXdeWvoCRo8rBbQ6VHBzw"
  })
  void shouldNotThrowExceptionIfItIsValid(String rssUrl) {
    assertDoesNotThrow(() -> new Url(rssUrl));
  }

  @ParameterizedTest
  @ValueSource(strings = {"**http://google.es", "http:rss///"})
  void shouldThrowExceptionIfItIsNotValid(String rssUrl) {
    assertThrows(InvalidUrl.class, () -> new Url(rssUrl));
  }

}
