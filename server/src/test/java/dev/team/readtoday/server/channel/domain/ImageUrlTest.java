package dev.team.readtoday.server.channel.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class ImageUrlTest {

  @Test
  void shouldNotThrowExceptionIfItIsValid() {
    assertDoesNotThrow(ImageUrlMother::getPNG);
    assertDoesNotThrow(ImageUrlMother::getJPG);
  }

  @Test
  void shouldThrowExceptionIfItIsValid() {
    assertThrows(InvalidImageUrl.class, ImageUrlMother::getInvalidImageURL);
  }
}
