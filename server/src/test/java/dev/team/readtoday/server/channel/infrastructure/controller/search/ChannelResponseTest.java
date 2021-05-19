package dev.team.readtoday.server.channel.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class ChannelResponseTest {

  @Test
  void shouldGetCorrectly() {

    Channel channel1 = ChannelMother.random();
    Channel channel2 = ChannelMother.random();

    Collection<Channel> channels = Arrays.asList(channel1, channel2);

    Collection<ChannelResponse> channelResponses = ChannelResponse.fromChannels(channels);

    Collection<Channel> channelsMapped = channelResponses.stream().map(channelResponse -> new Channel(
        ChannelId.fromString(channelResponse.getId()),
        new ChannelTitle(channelResponse.getTitle()),
        new RssUrl(channelResponse.getRssUrl()),
        new ChannelDescription(channelResponse.getDescription()),
        new ImageUrl(channelResponse.getImageUrl()),
        channelResponse.getCategoryIds().stream().map(CategoryId::fromString).toList()
    )).collect(Collectors.toList());

    assertEquals(channels, channelsMapped);
  }
}
