package dev.team.readtoday.server.channel.application.edit;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.shared.domain.Identifier;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import java.util.Collection;
import java.util.stream.Collectors;

public enum EditChannelCommandMother {
  ;

  public static Command withId(String id) {

    Channel channel = ChannelMother.random();

    Collection<String> categories = channel.getCategories().stream().map(Identifier::toString)
        .collect(Collectors.toList());

    return new EditChannelCommand(
        id,
        channel.getTitle().toString(),
        channel.getRssUrl().toString(),
        channel.getDescription().toString(),
        channel.getImageUrl().toString(),
        categories
    );
  }
}
