package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.URLValueObject;
import java.io.IOException;
import java.net.URL;

public class RssURL extends URLValueObject {

  public RssURL(URL value) {
    super(value);
  }

  private static boolean isValid(URL value) {
    return false;
  }
}
