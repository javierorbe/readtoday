package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import java.util.List;

public enum ChannelMother {
  ;

  private static final Faker faker = Faker.instance();

  public static Channel random() {

    return new Channel(
        ChannelId.random(),
        new ChannelTitle(faker.bothify("????##")),
        UrlMother.random(),
        new ChannelDescription(faker.bothify("??#? ?##? ##??")),
        UrlMother.random(),
        List.of()
    );
  }

}
