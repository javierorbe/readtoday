package dev.team.readtoday.server.channel.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class ChannelsByCategoryResponseTest {

  @Test
  void shouldGetCorrectly() {
    Channel channel1 = ChannelMother.random();
    Channel channel2 = ChannelMother.random();
    Collection<Channel> channels = Arrays.asList(channel1, channel2);

    Category category1 = CategoryMother.random();
    Category category2 = CategoryMother.random();
    Collection<Category> categories = Arrays.asList(category1, category2);

    ChannelsByCategoryResponse response = new ChannelsByCategoryResponse(channels, categories);

    Collection<ChannelResponse> channelResponses = response.getChannels();
    Collection<CategoryResponse> categoryResponses = response.getCategories();

    Collection<Channel> channelsMapped = mapToChannels(channelResponses);
    Collection<Category> categoriesMapped = mapToCategories(categoryResponses);

    assertAll(
        () -> assertEquals(channels, channelsMapped),
        () -> assertEquals(categories, categoriesMapped)
    );
  }

  private static Collection<Channel> mapToChannels(Collection<ChannelResponse> channelResponses) {

    return channelResponses.stream().map(channelResponse -> new Channel(
        ChannelId.fromString(channelResponse.getId()),
        new ChannelTitle(channelResponse.getTitle()),
        new RssUrl(channelResponse.getRssUrl()),
        new ChannelDescription(channelResponse.getDescription()),
        new ImageUrl(channelResponse.getImageUrl()),
        Collections.emptyList()
    )).collect(Collectors.toList());
  }

  private static Collection<Category> mapToCategories(Collection<CategoryResponse> categoryResponses) {
    return categoryResponses.stream().map(categoryResponse -> {
      String id = categoryResponse.getId();
      String name = categoryResponse.getName();
      return new Category(CategoryId.fromString(id), new CategoryName(name));
    }).collect(Collectors.toList());
  }
}
