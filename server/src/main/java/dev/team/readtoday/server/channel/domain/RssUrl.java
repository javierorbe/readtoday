package dev.team.readtoday.server.channel.domain;

import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import dev.team.readtoday.server.shared.domain.StringValueObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class RssUrl extends StringValueObject {

  public RssUrl(String value) {
    super(value);

    try {
      new URL(value);
    } catch (MalformedURLException e) {
      throw new InvalidRssUrl("Invalid URL.", e);
    }
  }

  public static RssUrl create(String value) {
    try {
      URL url = new URL(value);
      parseRssContent(url);
      return new RssUrl(value);
    } catch (MalformedURLException e) {
      throw new InvalidRssUrl("Invalid URL.", e);
    } catch (FeedException | IOException e) {
      throw new InvalidRssUrl("Invalid RSS content.", e);
    }
  }

  private static void parseRssContent(URL url) throws IOException, FeedException {
    SyndFeedInput input = new SyndFeedInput();
    input.build(new XmlReader(url));
  }
}
