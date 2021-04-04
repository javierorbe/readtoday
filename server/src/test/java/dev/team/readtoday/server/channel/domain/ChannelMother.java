package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.Set;

public enum ChannelMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static Channel random() {
    return new Channel(
        ChannelId.random(),
        new ChannelTitle(FAKER.bothify("????##")),
        RssUrlMother.random(),
        new ChannelDescription(FAKER.bothify("??#? ?##? ##??")),
        ImageUrlMother.random(),
        List.of()
    );
  }

  public static Channel withId(ChannelId id) {
    return new Channel(
        id,
        new ChannelTitle(FAKER.bothify("????##")),
        RssUrlMother.random(),
        new ChannelDescription(FAKER.bothify("??#? ?##? ##??")),
        ImageUrlMother.random(),
        Set.of()
    );
  }

  public static Channel withIdAndTitle(ChannelId id, ChannelTitle title) {
    return new Channel(
        id,
        title,
        RssUrlMother.random(),
        new ChannelDescription(FAKER.bothify("??#? ?##? ##??")),
        ImageUrlMother.random(),
        Set.of()
    );
  }
}
