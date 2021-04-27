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
final class ImageUrlTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "https://i.pinimg.com/originals/ca/76/0b/ca760b70976b52578da88e06973af542.jpg",
      "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"
  })
  void shouldNotThrowExceptionIfItIsValid(String url) {
    assertDoesNotThrow(() -> ImageUrl.create(url));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "hps:/images.com/example.png",
      "https://www.google.es/"
  })
  void shouldThrowExceptionIfItIsNotValid(String url) {
    assertThrows(InvalidImageUrl.class, () -> ImageUrl.create(url));
  }
}
