package dev.team.readtoday.server.channel.domain;

import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import dev.team.readtoday.server.shared.domain.UrlValueObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class RssUrl extends UrlValueObject {

  public RssUrl(URL value) {
    super(value);
    if (!isContentValid(value)) {
      throw new InvalidRssUrl("Error reading the feed url: " + value);
    }
  }

  private static boolean isContentValid(URL value) {
    try {
      SyndFeedInput input = new SyndFeedInput();
      input.build(new XmlReader(value));
      return true;
    } catch (IOException | FeedException e) {
      return false;
    }
  }

  public static RssUrl fromString(String value) throws MalformedURLException {
    return new RssUrl(new URL(value));
  }
}
