package dev.team.readtoday.server.channel.infrastructure.controller.create;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.channel.domain.ImageUrlMother;
import dev.team.readtoday.server.channel.domain.RssUrlMother;
import java.util.Collections;
import java.util.List;

public enum ChannelCreationRequestMother {

  ;

  private static final Faker faker = Faker.instance();

  public static ChannelCreationRequest random() {
    ChannelCreationRequest request = new ChannelCreationRequest();
    String title = faker.bothify("title ??????");
    String description = faker.bothify("description ??????");
    String rssUrl = RssUrlMother.random().toString();
    String imageUrl = ImageUrlMother.random().toString();
    List<String> categoryIds = Collections.emptyList();

    request.setTitle(title);
    request.setDescription(description);
    request.setRssUrl(rssUrl);
    request.setImageUrl(imageUrl);
    request.setCategoryIds(categoryIds);

    return request;
  }
}
