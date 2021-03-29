package dev.team.readtoday.server.channel.domain;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import dev.team.readtoday.server.shared.domain.URLValueObject;
import java.io.IOException;
import java.net.URL;

public class RssURL extends URLValueObject {

  public RssURL(URL value) {
    super(value);
    if (!isContentValid(value)) {
      throw new InvalidRssURL("Error reading the feed url: " + value);
    }
  }

  private static boolean isContentValid(URL value) {
    try {
      SyndFeedInput input = new SyndFeedInput();
      input.build(new XmlReader(value));
      return true;
    } catch (FeedException | IOException e) {
      return false;
    }
  }
}
