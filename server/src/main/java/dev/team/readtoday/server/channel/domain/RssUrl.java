package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;
import org.apache.commons.validator.routines.UrlValidator;

public class RssUrl extends StringValueObject {

  public RssUrl(String value) {
    super(value);
    if (!isValidRssUrlAddress(value)) {
      throw new InvalidUrl("Invalid rss url: " + value);
    }
  }

  private static boolean isValidRssUrlAddress(String value) {
    return UrlValidator.getInstance().isValid(value);
  }
}
