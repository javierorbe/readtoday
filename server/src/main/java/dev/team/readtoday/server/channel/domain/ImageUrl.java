package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;
import org.apache.commons.validator.routines.UrlValidator;

public class ImageUrl extends StringValueObject {

  public ImageUrl(String value) {
    super(value);
    if (!isValidImageUrl(value)) {
      throw new InvalidUrl("Invalid image url: " + value);
    }
  }

  private static boolean isValidImageUrl(String value) {
    return UrlValidator.getInstance().isValid(value) && UrlExtensionValidator.isImage(value);
  }
}
