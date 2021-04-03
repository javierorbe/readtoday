package dev.team.readtoday.server.channel.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class ChannelTitleTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "Reddit",
      "The New York Times"
  })
  void shouldCreateIfTitleIsValid(String title) {
    assertDoesNotThrow(() -> new ChannelTitle(title));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "This is too long of a channel title",
      "This is also not a valid channel title"
  })
  void shouldThrowExceptionIfTitleInvalid(String title) {
    assertThrows(InvalidChannelTitle.class, () -> new ChannelTitle(title));
  }
}
