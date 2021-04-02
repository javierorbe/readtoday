package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CategoryId;
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
        RssUrlMother.get(i++),
        new ChannelDescription(faker.bothify("??#? ?##? ##??")),
        ImageUrlMother.getPNG(),
        List.of()
    );
  }

  public static Channel randomWithCategories(List<CategoryId> ids) throws MalformedURLException {
    return new Channel(
        ChannelId.random(),
        new ChannelTitle(faker.bothify("????##")),
        RssUrlMother.get(i++),
        new ChannelDescription(faker.bothify("??#? ?##? ##??")),
        ImageUrlMother.getPNG(),
        ids
    );
  }
}
