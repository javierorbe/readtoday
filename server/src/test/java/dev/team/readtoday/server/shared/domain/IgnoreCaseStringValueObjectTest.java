package dev.team.readtoday.server.shared.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestMethodOrder(MethodOrderer.Random.class)
final class IgnoreCaseStringValueObjectTest {

  @Test
  void shouldNotBeEqualToNull() {
    StringValueObject strVal = new StringValueObject("someValue");
    assertFalse(strVal.equals(null));
  }

  @ParameterizedTest
  @CsvSource(value = {
      "heLLo,hEllo",
      "ThIS is someTHING,THIS IS something"
  }, delimiter = ',')
  void shouldEqualIgnoringCase(String first, String second) {
    assertEquals(new IgnoreCaseStringValueObject(first), new IgnoreCaseStringValueObject(second));
  }

  @ParameterizedTest
  @CsvSource(value = {
      "heLLo,hAllo",
      "ThIS is someTHING,THIS is NOthing"
  }, delimiter = ',')
  void shouldNotEqualIfDifferent(String first, String second) {
    assertNotEquals(new IgnoreCaseStringValueObject(first), new IgnoreCaseStringValueObject(second));
  }
}
