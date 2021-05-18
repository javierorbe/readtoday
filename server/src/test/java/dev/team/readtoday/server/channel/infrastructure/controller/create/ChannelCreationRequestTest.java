package dev.team.readtoday.server.channel.infrastructure.controller.create;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class ChannelCreationRequestTest {

  private static final Faker faker = Faker.instance();

  @Test
  void shouldSetAndGetCorrectly() {
    ChannelCreationRequest request = new ChannelCreationRequest();
    String title = faker.bothify("title ??????");
    String description = faker.bothify("description ??????");
    String rssUrl = faker.bothify("rss Url ??????");
    String imageUrl = faker.bothify("rss Url ??????");
    List<String> categoryIds = Collections.emptyList();

    request.setTitle(title);
    request.setDescription(description);
    request.setRssUrl(rssUrl);
    request.setImageUrl(imageUrl);
    request.setCategoryIds(categoryIds);

    assertAll(
        () -> assertEquals(title, request.getTitle()),
        () -> assertEquals(description, request.getDescription()),
        () -> assertEquals(rssUrl, request.getRssUrl()),
        () -> assertEquals(imageUrl, request.getImageUrl()),
        () -> assertEquals(categoryIds, request.getCategoryIds())
    );
  }
}
