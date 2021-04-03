package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

public enum ChannelMother {
  ;

  private static final Faker faker = Faker.instance();

  public static Channel random() throws MalformedURLException {
    return new Channel(
        ChannelId.random(),
        new ChannelTitle(faker.bothify("????##")),
        RssUrlMother.random(),
        new ChannelDescription(faker.bothify("??#? ?##? ##??")),
        ImageUrlMother.random(),
        List.of()
    );
  }

  public static Channel withIdAndTitle(ChannelId id, ChannelTitle title) {
    return new Channel(
        id,
        title,
        RssUrlMother.random(),
        new ChannelDescription(faker.bothify("??#? ?##? ##??")),
        ImageUrlMother.random(),
        Set.of()
    );
  }
}
