package dev.team.readtoday.server.shared.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class StringValueObjectTest {

  @Test
  void shouldNotBeEqualToNull() {
    StringValueObject strVal = new StringValueObject("someValue");
    assertFalse(strVal.equals(null));
  }

  @ParameterizedTest
  @MethodSource("provideMatchingStringAndPattern")
  void shouldNotThrowExceptionIfValueMatchesThePattern(String value, Pattern pattern) {
    assertDoesNotThrow(() -> new StringValueObject(value, pattern));
  }

  private static Stream<Arguments> provideMatchingStringAndPattern() {
    return Stream.of(
        Arguments.of("bubble", Pattern.compile("b[aeiou]bble")),
        Arguments.of("babble", Pattern.compile("b[aeiou]bble")),
        Arguments.of("ar4", Pattern.compile("a.[0-9]"))
    );
  }

  @ParameterizedTest
  @MethodSource("provideNotMatchingStringAndPattern")
  void shouldThrowExceptionIfValueDoesNotMatchThePattern(String value, Pattern pattern) {
    assertThrows(IllegalArgumentException.class, () -> new StringValueObject(value, pattern));
  }

  private static Stream<Arguments> provideNotMatchingStringAndPattern() {
    return Stream.of(
        Arguments.of("bwbble", Pattern.compile("b[aeiou]bble")),
        Arguments.of("bablbe", Pattern.compile("b[aeiou]bble")),
        Arguments.of("awi", Pattern.compile("a.[0-9]"))
    );
  }
}
