package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import com.google.common.collect.ImmutableList;

public enum RssUrlMother {
  ;

  private static final ImmutableList<RssUrl> URLS = ImmutableList.of(
      new RssUrl("https://metricool.com/feed/"),
      new RssUrl("https://www.josefacchin.com/feed/"),
      new RssUrl("https://e00-marca.uecdn.es/rss/futbol/athletic.xml")
  );

  private static final Faker FAKER = Faker.instance();

  public static RssUrl random() {
    return URLS.get(FAKER.random().nextInt(URLS.size()));
  }
}
