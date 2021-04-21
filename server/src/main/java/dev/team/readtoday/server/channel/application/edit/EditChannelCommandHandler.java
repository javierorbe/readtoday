package dev.team.readtoday.server.channel.application.edit;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EditChannelCommandHandler implements CommandHandler<EditChannelCommand> {

  private final EditChannel editChannel;

  public EditChannelCommandHandler(EditChannel editChannel) {
    this.editChannel = editChannel;
  }

  @Override
  public void handle(EditChannelCommand command) {
    Collection<CategoryId> categories = command.getCategories().stream()
        .map(CategoryId::fromString)
        .collect(Collectors.toSet());

    Channel channel = new Channel(
        ChannelId.fromString(command.getId()),
        new ChannelTitle(command.getTitle()),
        new RssUrl(command.getRssUrl()),
        new ChannelDescription(command.getDescription()),
        ImageUrl.create(command.getImageUrl()),
        categories
    );

    editChannel.edit(channel);
  }
}
