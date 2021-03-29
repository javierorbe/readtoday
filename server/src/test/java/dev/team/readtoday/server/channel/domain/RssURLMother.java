package dev.team.readtoday.server.channel.domain;

import java.net.MalformedURLException;
import java.net.URL;

public enum RssURLMother {
  ;

  private static final String [] urls = {
      "https://metricool.com/feed/",
      "https://www.josefacchin.com/feed/",
      "https://e00-marca.uecdn.es/rss/futbol/athletic.xml"
  };


  static RssURL get(int i) throws MalformedURLException {
    return new RssURL(new URL(urls[i]));
  }

}
