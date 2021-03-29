package dev.team.readtoday.server.channel.domain;

import java.net.MalformedURLException;
import java.net.URL;

public enum RssUrlMother {
  ;

  private static final String [] urls = {
      "https://metricool.com/feed/",
      "https://www.josefacchin.com/feed/",
      "https://e00-marca.uecdn.es/rss/futbol/athletic.xml"
  };


  static RssUrl get(int i) throws MalformedURLException {
    return new RssUrl(new URL(urls[i]));
  }

}
