package dev.team.readtoday.client.usecase.channel.edit.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class EditChannelRequestTest {

  @Test
  void shouldSaveCorrectly() {
    String title = "some title123";
    String rssUrl = "https://adlfkjalkdfj.com";
    String description = "some description123123";
    String imageUrl = "https://dalskjdsalk.com";
    Collection<String> categoryIds = Collections.emptyList();

    EditChannelRequest request = new EditChannelRequest(
        title,
        rssUrl,
        description,
        imageUrl,
        categoryIds
    );

    assertEquals(title, request.getTitle());
    assertEquals(rssUrl, request.getRssUrl());
    assertEquals(description, request.getDescription());
    assertEquals(imageUrl, request.getImageUrl());
    assertEquals(categoryIds, request.getCategoryIds());
  }
}
