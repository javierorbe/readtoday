package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import java.net.MalformedURLException;
import java.util.List;

public enum ChannelMother {
  ;

  private static final Faker faker = Faker.instance();
  private static int i = 0;

  public static Channel random() throws MalformedURLException {
    return new Channel(
        ChannelId.random(),
        new ChannelTitle(faker.bothify("????##")),
        RssURLMother.get(i++),
        new ChannelDescription(faker.bothify("??#? ?##? ##??")),
        ImageURLMother.getPNG(),
        List.of()
    );
  }
}
