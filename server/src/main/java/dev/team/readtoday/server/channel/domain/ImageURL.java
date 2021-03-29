package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.URLValueObject;
import java.net.URL;

public class ImageURL extends URLValueObject {

  public ImageURL(URL value) {
    super(value);
  }

  private static boolean isValid(URL value) {

    // TODO: Verify if content is an image
    return false;
  }
}
