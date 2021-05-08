package dev.team.readtoday.server.customlist.application;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListTitle;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;
import java.util.Collections;

@Service
public class CreateCustomListCommandHandler implements CommandHandler<CreateCustomListCommand> {

  private final CreateCustomList createCustomList;

  public CreateCustomListCommandHandler(CreateCustomList createCustomList) {
    this.createCustomList = createCustomList;
  }

  @Override
  public void handle(CreateCustomListCommand command) {
    CustomList customList = new CustomList(
        CustomListId.random(),
        new CustomListTitle(command.getTitle()),
        UserId.fromString(command.getUserId()),
        Collections.emptyList()
    );

    createCustomList.save(customList);
  }
}
