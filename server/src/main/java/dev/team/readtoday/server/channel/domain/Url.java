package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;
import org.apache.commons.validator.routines.UrlValidator;

public final class Url extends StringValueObject {

  public Url(String value) {
    super(value);
    if (!isValidUrl(value)) {
      throw new InvalidUrl("Invalid url: " + value);
    }
  }

  private static boolean isValidUrl(String value) {
    return UrlValidator.getInstance().isValid(value);
  }
}
