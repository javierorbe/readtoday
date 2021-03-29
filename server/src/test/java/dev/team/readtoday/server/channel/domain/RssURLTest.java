package dev.team.readtoday.server.channel.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class RssURLTest {

  @Test
  void shouldNotThrowExceptionIfItIsValid() {
    assertDoesNotThrow(() -> RssURLMother.get(0));
    assertDoesNotThrow(() -> RssURLMother.get(1));
    assertDoesNotThrow(() -> RssURLMother.get(2));
  }

  @ParameterizedTest
  @ValueSource(strings = {"https://www.google.es/"})
  void shouldThrowExceptionIfItIsNotValid(String rssUrl) {
    assertThrows(InvalidRssURL.class, () -> new RssURL(new URL(rssUrl)));
  }
}
