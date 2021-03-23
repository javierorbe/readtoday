package dev.team.readtoday.server.user.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;
import org.apache.commons.validator.routines.EmailValidator;

public final class EmailAddress extends StringValueObject {

  /**
   * @param value the email address string
   * @throws InvalidEmailAddress if the value is not a valid email address
   */
  public EmailAddress(String value) {
    super(value);
    if (!isValidEmailAddress(value)) {
      throw new InvalidEmailAddress("Invalid email address: " + value);
    }
  }

  private static boolean isValidEmailAddress(String value) {
    return EmailValidator.getInstance().isValid(value);
  }
}
